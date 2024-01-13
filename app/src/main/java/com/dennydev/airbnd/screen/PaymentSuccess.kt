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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dennydev.airbnd.R
import com.dennydev.airbnd.model.common.ApiResponse
import com.dennydev.airbnd.viewmodel.PaymentViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentSuccess(
    navController: NavHostController,
    orderId: String
) {
    Scaffold(modifier = Modifier
        .fillMaxSize(), topBar = {
        CenterAlignedTopAppBar(title = {  }, actions = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "close")
            }
        })
    }) { values ->
      Column(modifier=Modifier.fillMaxSize().padding(values).padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
          Image(painter = painterResource(id = R.drawable.success), contentDescription = "", modifier = Modifier.width(250.dp).aspectRatio(1f))
          Spacer(modifier=Modifier.height(24.dp))
          Text("Payment Success.", style=MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
          Spacer(modifier=Modifier.height(8.dp))
          Text("Thanks for choosing us.", style=MaterialTheme.typography.titleLarge)
      }
    }
}