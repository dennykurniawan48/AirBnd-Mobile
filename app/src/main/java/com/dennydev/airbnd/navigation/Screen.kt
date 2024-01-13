package com.dennydev.airbnd.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val unselectedIcon: ImageVector, val selectedIcon: ImageVector, val route: String, val title: String) {
    object HomeScreen : Screen(Icons.Default.Home, Icons.Default.Home, "Home", "Home")
    object LoginScreen : Screen(Icons.Default.Check, Icons.Default.Check, "Login", "Login")
    object RegisterScreen : Screen(Icons.Default.Check, Icons.Default.Check, "Register", "Register")
    object DetailPropertyScreen : Screen(Icons.Default.Check, Icons.Default.Check, "Property/{slug}", "Property")
    object ReserveScreen: Screen(Icons.Default.Check, Icons.Default.Check, "Reserve/{slug}", "Reserve")
    object PaymentScreen: Screen(Icons.Default.Check, Icons.Default.Check, "Payment/{id}", "Payment")
    object PaymentSuccessScreen: Screen(Icons.Default.Check, Icons.Default.Check, "Success/{id}", "Success")
    object HistoryScreen: Screen(Icons.Default.Check, Icons.Default.Check, "History", "History")
}