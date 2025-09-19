package com.example.grocerylist.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNav(navController: NavHostController, modifier: Modifier = Modifier) {
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
fun AppNavPreview() {
    val navController = rememberNavController()
    AppNav(navController)
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
fun AppNavBarPreview() {
    val navController = rememberNavController()
    AppNavBar(navController)
}