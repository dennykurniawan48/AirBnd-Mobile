package com.dennydev.airbnd.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.dennydev.airbnd.model.common.ApiResponse
import com.dennydev.airbnd.model.form.OrderForm
import com.dennydev.airbnd.navigation.Screen
import com.dennydev.airbnd.viewmodel.HomeViewModel
import com.dennydev.airbnd.viewmodel.MainViewModel
import com.dennydev.airbnd.viewmodel.PaymentViewModel
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.stripe.android.paymentsheet.rememberPaymentSheet
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    navController: NavHostController,
    orderId: String,
    mainViewModel: MainViewModel,
    viewModel: PaymentViewModel = hiltViewModel()
) {
    val cartSession by viewModel.cartSession
    val paymentSheet = rememberPaymentSheet(paymentResultCallback = viewModel::onPaymentSheetResult)
    val checkoutResult by viewModel.checkoutState.collectAsState()
    val context = LocalContext.current
    val detailOrder by viewModel.order
    val lifecycleOwner = LocalLifecycleOwner.current
    val scope = rememberCoroutineScope()

    DisposableEffect(lifecycleOwner) {
        // Create an observer that triggers our remembered callbacks
        // for sending analytics events
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                scope.launch {
                    mainViewModel.token.collectLatest {
                        viewModel.getOrder(token = it, orderId)
                    }
                }
            }
        }

        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(key1 = checkoutResult){
        if(checkoutResult == PaymentSheetResult.Completed){
            navController.navigate(Screen.PaymentSuccessScreen.route.replace("{id}", orderId)){
                popUpTo(Screen.HomeScreen.route){
                    inclusive=false
                }
            }
        }
    }

    LaunchedEffect(key1 = cartSession) {
        if (cartSession is ApiResponse.Success) {
            cartSession.data?.data?.let {
                val configuration = PaymentSheet.CustomerConfiguration(
                    it.customer, it.ephemeralKey
                )
                PaymentConfiguration.init(context, it.publishableKey)
                paymentSheet.presentWithPaymentIntent(
                    it.paymentIntent, PaymentSheet.Configuration(
                        "AirBnd", customer = configuration
                    )
                )
            }
        }
    }

    Scaffold(modifier = Modifier
        .fillMaxSize(), topBar = {
        TopAppBar(title = { Text("Detail Order") }, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Default.ArrowBackIos, contentDescription = "")
            }
        })
    }, bottomBar = {
        BottomAppBar {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)){
                Button(onClick = {
                    scope.launch {
                        mainViewModel.token.collectLatest {
                            viewModel.getPaymentSheetData(token = it, orderId)
                        }
                    }
                }, modifier=Modifier.fillMaxWidth(),
                    enabled = detailOrder is ApiResponse.Success && detailOrder.data?.data?.paid == 0) {
                    if(detailOrder is ApiResponse.Success){
                        detailOrder.data?.data?.let {
                            Text(text = "Pay now: $${"%.2f".format(it.total)}")
                        }
                    }else{
                        Text(text = "Pay now")
                    }
                }
            }
        }
    }){ values ->
        detailOrder.data?.data?.let {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(values)
                .padding(horizontal = 16.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(), elevation = CardDefaults.cardElevation(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
                ) {
                    val painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(context = context)
                            .data(it.property.image)
                            .build()
                    )
                    Image(
                        painter = painter, contentDescription = "", modifier = Modifier
                            .width(100.dp)
                            .aspectRatio(1f)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column() {
                        Text("All unit", style = MaterialTheme.typography.labelMedium)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = it.property.name,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "${it.property.area.name}, ${it.property.area.country.name}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(36.dp))
            Text(
                "Your travel",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "Date",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row {
                        Text(
                            it.start,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(" - ", style = MaterialTheme.typography.bodyMedium)
                        Text(
                            it.end,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "Guest",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "${it.adult} adults ${if (it.kids > 0) "and ${it.kids} childrens" else ""}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Divider(modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Total",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    "$${
                        "%.2f".format(
                            it.total
                        )
                    }", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold
                )
            }
        }
        }
    }
}