package com.example.grocerylist

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.grocerylist.checkout.CheckoutScreenPreview
import com.example.grocerylist.settings.SettingsScreenPreview
import kotlinx.serialization.Serializable

@Serializable
sealed interface Destination {
    @Serializable
    object Checkout : Destination

    @Serializable
    object Settings : Destination
}

data class NavBarItem(
    val destination: Destination,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
    val label: String? = null,
    val content: @Composable () -> Unit
)

val BOTTOM_NAVIGATION_DESTINATIONS = listOf(
    NavBarItem(
        Destination.Checkout, Icons.Outlined.ShoppingCart, Icons.Filled.ShoppingCart, "Groceries"
    ) { CheckoutScreenPreview() },

    NavBarItem(
        Destination.Settings, Icons.Outlined.Settings, Icons.Filled.Settings, "Settings"
    ) { SettingsScreenPreview() },
)

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController, startDestination = Destination.Checkout, modifier = modifier
    ) {
        BOTTOM_NAVIGATION_DESTINATIONS.forEach { item ->
            composable(item.destination::class) { item.content() }
        }
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
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    NavigationBar {
        BOTTOM_NAVIGATION_DESTINATIONS.forEach { item ->
            val isSelected = currentDestination?.hasRoute(item.destination::class) == true
            NavigationBarItem(isSelected, {
                if (isSelected) {
                    return@NavigationBarItem
                }

                navController.navigate(item.destination) {
                    launchSingleTop = true
                    restoreState = true
                }
            }, icon = {
                Icon(
                    if (isSelected) item.selectedIcon else item.unselectedIcon,
                    contentDescription = "Bottom navigation icon"
                )
            }, label = {
                if (item.label != null) {
                    Text(
                        item.label,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            })
        }
    }
}

@Composable
@Preview
fun GroceryNavBarPreview() {
    val navController = rememberNavController()
    AppNavBar(navController)
}