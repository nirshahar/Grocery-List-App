package com.example.grocerylist.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {

    @Serializable
    object Main : Route {
        @Serializable
        object Home : Route

        @Serializable
        object Edit : Route

        @Serializable
        object Settings : Route
    }

    @Serializable
    object Checkout : Route

    fun matchesNavDestination(navDestination: NavDestination?): Boolean =
        navDestination?.hierarchy?.any { it.hasRoute(this::class) } ?: false
}

enum class BottomNavigationItem(
    val route: Route,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
    val label: String = "",
) {
    Home(
        Route.Main.Home,
        Icons.Outlined.Home,
        Icons.Filled.Home,
        "Home"
    ),

    Edit(
        Route.Main.Edit,
        Icons.Outlined.Edit,
        Icons.Filled.Edit,
        "Groceries"
    ),

    Settings(
        Route.Main.Settings,
        Icons.Outlined.Settings,
        Icons.Filled.Settings,
        "Settings"
    );

    fun matchesNavDestination(navDestination: NavDestination?): Boolean =
        route.matchesNavDestination(navDestination)
}