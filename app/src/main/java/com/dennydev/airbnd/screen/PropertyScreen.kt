package com.dennydev.airbnd.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import com.dennydev.airbnd.component.product.Content
import com.dennydev.airbnd.component.product.Header
import com.dennydev.airbnd.component.product.Title
import com.dennydev.airbnd.component.product.TopBar
import com.dennydev.airbnd.component.shimmerEffect
import com.dennydev.airbnd.model.common.ApiResponse
import com.dennydev.airbnd.navigation.Screen
import com.dennydev.airbnd.viewmodel.PropertyViewModel
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.roundToInt

@Composable
fun PropertyScreen(
    navController: NavHostController,
    propertyId: String,
    propertyViewModel: PropertyViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val property by propertyViewModel.property
    val snackbarHostState = remember{ SnackbarHostState() }

    val screenWithDP = with(LocalConfiguration.current) {
        screenWidthDp.dp
    }
    val screenWidthPx = with(LocalDensity.current){screenWithDP.toPx()}
    val topBarHeightPx = with(LocalDensity.current){56.dp.toPx()}
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    DisposableEffect(lifecycleOwner) {
        // Create an observer that triggers our remembered callbacks
        // for sending analytics events
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                if(property !is ApiResponse.Success) {
                    propertyViewModel.getDetailProperty(propertyId)
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

    Scaffold(modifier = Modifier
        .fillMaxSize(), snackbarHost = { SnackbarHost(hostState = snackbarHostState) }, bottomBar = {
           BottomAppBar() {
               Row(modifier = Modifier
                   .fillMaxWidth()
                   .padding(horizontal = 24.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                   if (property is ApiResponse.Success) {
                       property.data?.data?.property?.let {
                           Row {
                               Text("$")
                               Text(text = "%.2f".format(it.price))
                               Text(text = " / night.")
                           }
                       }
                   }else{
                       Box(modifier = Modifier
                           .width(100.dp)
                           .height(35.dp)
                           .background(shimmerEffect()))
                   }
                   Button(
                       onClick = {
                                 navController.navigate(Screen.ReserveScreen.route.replace("{slug}", propertyId))
                       }, modifier = Modifier, enabled = property is ApiResponse.Success
                   ) {
                       Text("Reserve")
                   }
               }
           }
    }){ values->
        if(property is ApiResponse.Success) {
            property.data?.data?.let {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(values)
                ) {
                    Header(it.property.image, scrollState,screenWidthPx, context)
                    Content(it, screenWithDP, scrollState)
                    TopBar(scrollState, screenWidthPx, topBarHeightPx, navController)
                    Title(scrollState, headerHeightPx = screenWidthPx, toolbarHeightPx = topBarHeightPx, headerHeight = screenWithDP, data = it)
                }
            }
        }else{
            Column(
                modifier=Modifier.fillMaxSize().padding(values),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(text = "Loading...")
            }
        }
    }
}

