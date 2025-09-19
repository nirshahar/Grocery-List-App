package com.example.grocerylist.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
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
