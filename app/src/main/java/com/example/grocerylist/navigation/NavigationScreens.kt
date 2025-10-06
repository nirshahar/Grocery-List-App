package com.example.grocerylist.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    @Serializable
    object Checkout : Route

    @Serializable
    object Edit : Route

    @Serializable
    object Settings : Route

    fun matchesNavDestination(navDestination: NavDestination?): Boolean =
        navDestination?.hasRoute(this::class) ?: false
}

enum class BottomNavigationItem(
    val route: Route,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
    val label: String? = null,
) {
    Checkout(
        Route.Checkout,
        Icons.Outlined.ShoppingCart,
        Icons.Filled.ShoppingCart,
        "Groceries"
    ),

    Edit(
        Route.Edit,
        Icons.Outlined.Edit,
        Icons.Filled.Edit,
        "Groceries"
    ),

    Settings(
        Route.Settings,
        Icons.Outlined.Settings,
        Icons.Filled.Settings,
        "Settings"
    );

    fun matchesNavDestination(navDestination: NavDestination?): Boolean =
        route.matchesNavDestination(navDestination)
}