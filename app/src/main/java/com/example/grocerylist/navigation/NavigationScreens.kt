package com.example.grocerylist.navigation

import androidx.annotation.StringRes
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
import com.example.grocerylist.R
import kotlinx.serialization.Serializable



@Serializable
sealed interface Screen : Route
@Serializable
sealed interface Route {

    @Serializable
    object Main : Route {
        @Serializable
        object Home : Screen

        @Serializable
        object Edit : Screen

        @Serializable
        object Settings : Screen
    }

    @Serializable
    object Checkout : Screen

    fun matchesNavDestination(navDestination: NavDestination?): Boolean =
        navDestination?.hierarchy?.any { it.hasRoute(this::class) } ?: false
}

val SCREENS = listOf<Screen>(
    Route.Main.Home,
    Route.Main.Edit,
    Route.Main.Settings,
    Route.Checkout
)

fun NavDestination.getCurrentScreen(): Screen? {
    return SCREENS.firstOrNull { it.matchesNavDestination(this) }
}

enum class BottomNavigationItem(
    val route: Route,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
    @param:StringRes val label: Int,
) {
    Home(
        Route.Main.Home,
        Icons.Outlined.Home,
        Icons.Filled.Home,
        R.string.bottomnav_home_label
    ),

    Edit(
        Route.Main.Edit,
        Icons.Outlined.Edit,
        Icons.Filled.Edit,
        R.string.bottomnav_groceries_label
    ),

    Settings(
        Route.Main.Settings,
        Icons.Outlined.Settings,
        Icons.Filled.Settings,
        R.string.bottomnav_settings_label
    );

    fun matchesNavDestination(navDestination: NavDestination?): Boolean =
        route.matchesNavDestination(navDestination)
}