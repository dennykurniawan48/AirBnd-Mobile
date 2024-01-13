package com.dennydev.airbnd.screen

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import com.dennydev.airbnd.component.AlertLogout
import com.dennydev.airbnd.component.ListCategory
import com.dennydev.airbnd.component.PlaceholderCategory
import com.dennydev.airbnd.component.PlaceholderHomeProperty
import com.dennydev.airbnd.component.Property
import com.dennydev.airbnd.component.googleSignOut
import com.dennydev.airbnd.model.common.ApiResponse
import com.dennydev.airbnd.navigation.Screen
import com.dennydev.airbnd.viewmodel.HomeViewModel
import com.dennydev.airbnd.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    mainViewModel: MainViewModel
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val selectedCategory by remember { homeViewModel.selectedCategory }
    val refresh by homeViewModel.refresh
    val categories by homeViewModel.categories
    val categoryProduct by homeViewModel.categoryProduct
    val showAlertLogout by homeViewModel.showAlertLogout
    val isGoogleLogin by mainViewModel.isGoogle.collectAsState(initial = false)
    val isSignedIn by mainViewModel.isSignedIn
    val context = LocalContext.current

    DisposableEffect(lifecycleOwner) {
        // Create an observer that triggers our remembered callbacks
        // for sending analytics events
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                if(categories !is ApiResponse.Success && categoryProduct !is ApiResponse.Success) {
                    homeViewModel.getAllCategory()
                    homeViewModel.getProductCategory(selectedCategory)
                }
            }
            if (event == Lifecycle.Event.ON_CREATE) {
                Log.d("pos", "create")
            }
        }

        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(isSignedIn){
        if(!mainViewModel.getSignedStatus()){
            navController.navigate(Screen.LoginScreen.route){
                popUpTo(Screen.LoginScreen.route){
                    inclusive = true
                }
            }
        }
    }

    LaunchedEffect(refresh){
        if(refresh){
            homeViewModel.getAllCategory()
            homeViewModel.getProductCategory(selectedCategory)
        }
    }

    Scaffold(modifier = Modifier
        .fillMaxSize(), topBar = {
        CenterAlignedTopAppBar(title = { Text("Home") }, actions = {
            IconButton(onClick = {
                navController.navigate(Screen.HistoryScreen.route)
            }) {
                Icon(imageVector = Icons.Default.History, contentDescription = "orders")
            }
            IconButton(onClick = {
                homeViewModel.changeShowAlertLogout(true)
            }) {
                Icon(imageVector = Icons.Default.Logout, contentDescription = "logout")
            }
        })
    }) { values ->
        if(showAlertLogout){
            AlertLogout(onCancel = { homeViewModel.changeShowAlertLogout(false) }) {
                if(isGoogleLogin){
                    googleSignOut(context = context){
                        mainViewModel.logout()
                    }
                }else{
                    mainViewModel.logout()
                }
                homeViewModel.changeShowAlertLogout(false)
            }
        }
    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier= Modifier
        .fillMaxSize()
        .padding(values)) {
        item(span = { GridItemSpan(2) }) {
            when (categories) {
                is ApiResponse.Success -> {
                    categories.data?.let {
                        ListCategory(data = it, onCategoryClicked = {
                            homeViewModel.changeSelectedCategory(it)
                            homeViewModel.getProductCategory(it)
                        })
                    }
                }

                else -> {
                    PlaceholderCategory()
                }
            }
        }

        if (categoryProduct is ApiResponse.Success) {
            categoryProduct.data?.let {
                items(it.properties) {
                    Property(navController = navController, property = it)
                }
            }

        } else {
            items(10){
                PlaceholderHomeProperty()
            }
        }
    }
    }
}