package com.example.grocerylist

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.grocerylist.checkout.CheckoutScreenPreview
import com.example.grocerylist.settings.SettingsScreenPreview
import kotlinx.serialization.Serializable

object Destinations {
    @Serializable
    object Checkout

    @Serializable
    object Settings
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Destinations.Checkout) {
        composable<Destinations.Checkout> { CheckoutScreenPreview() }
        composable<Destinations.Settings> { SettingsScreenPreview() }
    }
}

@Composable
@Preview
fun AppNavigationPreview() {
    val navController = rememberNavController()
    AppNavigation(navController)
}

@Composable
fun AppNavBar(navController: NavHostController) {
    BottomAppBar {
        NavigationBarItem(
            true,
            {
                navController.navigate(Destinations.Checkout)
            },
            icon = {
                Icon(Icons.Default.ShoppingCart, contentDescription = "Navigate to grocery list")
            },
            label = {
                Text("Groceries")
            }
        )

        NavigationBarItem(
            false,
            {
                navController.navigate(Destinations.Settings)
            },
            icon = {
                Icon(Icons.Default.Settings, contentDescription = "Navigate to settings")
            },
            label = {
                Text("Settings")
            }
        )
    }
}

@Composable
@Preview
fun GroceryNavBarPreview() {
    val navController = rememberNavController()
    AppNavBar(navController)
}