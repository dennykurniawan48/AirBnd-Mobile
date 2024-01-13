package com.dennydev.airbnd.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dennydev.airbnd.screen.HistoryScreen
import com.dennydev.airbnd.screen.HomeScreen
import com.dennydev.airbnd.screen.LoginScreen
import com.dennydev.airbnd.screen.PaymentScreen
import com.dennydev.airbnd.screen.PaymentSuccess
import com.dennydev.airbnd.screen.PropertyScreen
import com.dennydev.airbnd.screen.RegisterScreen
import com.dennydev.airbnd.screen.ReserveScreen
import com.dennydev.airbnd.viewmodel.MainViewModel

@Composable
fun SetupNavigation(navController: NavHostController, startDestination: String, mainViewModel: MainViewModel) {
    Log.d("pos s", startDestination)
    NavHost(navController = navController, startDestination = startDestination, modifier = Modifier) {
        composable(Screen.HomeScreen.route) { entry ->
            HomeScreen(
                navController = navController,
                mainViewModel = mainViewModel
            )
        }

        composable(Screen.LoginScreen.route) { entry ->
            LoginScreen(
                navController = navController
            )
        }

        composable(Screen.RegisterScreen.route){
            RegisterScreen(navController = navController)
        }
        composable(Screen.ReserveScreen.route, arguments = listOf(
            navArgument(name="slug"){
                type = NavType.StringType
        })){
            it.arguments?.getString("slug")?.let { slug ->
                ReserveScreen(propertyId = slug, navController = navController, mainViewModel = mainViewModel)
            }
        }

        composable(Screen.DetailPropertyScreen.route, arguments = listOf(
            navArgument(name="slug"){
                type = NavType.StringType
            }
        )){
            it.arguments?.getString("slug")?.let { slug ->
                PropertyScreen(navController = navController, propertyId = slug)
            }
        }

        composable(Screen.PaymentScreen.route, arguments = listOf(
            navArgument(name="id"){
                type = NavType.StringType
            }
        )){
            it.arguments?.getString("id")?.let { id ->
                PaymentScreen(navController = navController, orderId = id, mainViewModel = mainViewModel)
            }
        }

        composable(Screen.PaymentSuccessScreen.route, arguments = listOf(
            navArgument(name="id"){
                type = NavType.StringType
            }
        )){
            it.arguments?.getString("id")?.let { id ->
                PaymentSuccess(navController = navController, orderId = id)
            }
        }

        composable(Screen.HistoryScreen.route){
            HistoryScreen(navController = navController, mainViewModel = mainViewModel)
        }
    }
}