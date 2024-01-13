package com.dennydev.airbnd.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.dennydev.airbnd.component.AlertGuess
import com.dennydev.airbnd.model.common.ApiResponse
import com.dennydev.airbnd.model.form.OrderForm
import com.dennydev.airbnd.model.response.property.Data
import com.dennydev.airbnd.navigation.Screen
import com.dennydev.airbnd.viewmodel.MainViewModel
import com.dennydev.airbnd.viewmodel.PropertyViewModel
import com.dennydev.airbnd.viewmodel.ReserveViewModel
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReserveScreen(
    viewModel: ReserveViewModel = hiltViewModel(),
    propertyId: String,
    navController: NavHostController,
    mainViewModel: MainViewModel
) {
    val context = LocalContext.current
    val property by viewModel.property
    val lifecycleOwner = LocalLifecycleOwner.current
    val calendarSheet = rememberUseCaseState()
    val start by viewModel.startDate
    val end by viewModel.endDate
    val disabledDate by viewModel.disabledDate
    val overlapDate by viewModel.errorOverlap
    val showAlertGuest by viewModel.showAlertGuest
    val adult by viewModel.adultGuest
    val kids by viewModel.kidGuest
    val timeBoundary = LocalDate.now().let { now -> now..now.plusMonths(3) }
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)
    val token by mainViewModel.token.collectAsState(initial = "")
    val checkoutData by viewModel.checkoutResponse

    DisposableEffect(lifecycleOwner) {
        // Create an observer that triggers our remembered callbacks
        // for sending analytics events
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                viewModel.getDetailProperty(propertyId)
            }
        }

        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(key1 = checkoutData){
        if(checkoutData is ApiResponse.Success){
            checkoutData.data?.data?.let {
                navController.popBackStack()
                navController.navigate(Screen.PaymentScreen.route.replace("{id}", it.id.toString()))
            }
        }
    }

    Scaffold(modifier = Modifier
        .fillMaxSize(), topBar = {
        TopAppBar(title = { Text("Order") }, navigationIcon = {
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
                    viewModel.order(token, order = OrderForm(
                        adult=adult,
                        kid = kids,
                        property = propertyId,
                        start=start.toString(),
                        end=end.toString()
                    ))
                }, modifier=Modifier.fillMaxWidth(),
                    enabled = start != null && end != null && !overlapDate && checkoutData !is ApiResponse.Loading) {
                    Text("Order")
                }
            }
        }
    }){values ->
        CalendarDialog(
            state = calendarSheet,
            config = CalendarConfig(
                yearSelection = true,
                monthSelection = true,
                boundary = timeBoundary,
                style = CalendarStyle.MONTH,
                disabledDates = disabledDate
            ),
            selection = CalendarSelection.Period { startDate, endDate ->
                viewModel.setDate(startDate, endDate)
            },
        )
        if(showAlertGuest){
            AlertGuess(adult = adult, children = kids, onIncreaseAdult = {
                viewModel.increaseAdult()
            }, onDecreaseAdult = {
                viewModel.decreaseAdult()
            }, onIncreaseKid = {
                viewModel.increaseKid()
            }, onDecreaseKid = {
                viewModel.decreaseKid()
            }) {
                viewModel.setShowAlertGuest(false)
            }
        }
        property.data?.data?.let {
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
                            Text(text = it.property.name, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("${it.property.area.name}, ${it.property.area.country.name}", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(36.dp))
                Text("Your travel", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier= Modifier
                    .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text("Date", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Row {
                            Text(
                                if (start != null) formatter.format(start) else "Checkin",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(" - ", style = MaterialTheme.typography.bodyMedium)
                            Text(
                                if (end != null) formatter.format(end) else "Checkout",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        if(overlapDate){
                            Text(text = "* Invalid date, please choose another date.", style = MaterialTheme.typography.bodySmall, color=MaterialTheme.colorScheme.error)
                        }
                    }
                    TextButton(onClick = { calendarSheet.show() }) {
                        Text(text = "Change")
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))

                Row(modifier= Modifier
                    .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text("Guest", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("${adult} adults ${if(kids > 0) "and ${kids} childrens" else ""}", style = MaterialTheme.typography.bodyMedium)
                    }
                    TextButton(onClick = {
                        viewModel.setShowAlertGuest(true)
                    }) {
                        Text(text = "Change")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Divider(modifier=Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier=Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Total", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)

                    if(start != null && end != null){
                        Text("$${"%.2f".format(ChronoUnit.DAYS.between(start, end) * it.property.price)}",  style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                    }
                }

            }
        }
    }
}