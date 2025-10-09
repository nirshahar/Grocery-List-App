package com.example.grocerylist.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.grocerylist.SetFabAction
import com.example.grocerylist.screens.checkout.CheckoutScreenHolder
import com.example.grocerylist.screens.edit.EditScreenHolder
import com.example.grocerylist.screens.home.HomeScreenHolder
import com.example.grocerylist.screens.settings.SettingsScreenHolder

@Composable
fun AppNav(
    navController: NavHostController,
    setFabAction: SetFabAction,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController, startDestination = Route.Main, modifier = modifier
    ) {
        composable<Route.Checkout> {
            CheckoutScreenHolder(navController)
        }

        navigation<Route.Main>(startDestination = Route.Main.Home) {
            buildMainNavigation(navController, setFabAction)
        }
    }
}

fun NavGraphBuilder.buildMainNavigation(
    navController: NavHostController,
    setFabAction: SetFabAction,
) {
    BottomNavigationItem.entries.forEach { item ->
        composable(item.route::class) {
            when (item) {
                BottomNavigationItem.Home -> {
                    HomeScreenHolder(navController, setFabAction)
                }

                BottomNavigationItem.Edit -> {
                    EditScreenHolder(setFabAction)
                }

                BottomNavigationItem.Settings -> {
                    SettingsScreenHolder()
                }
            }
        }
    }
}

@Composable
@Preview
fun AppNavPreview() {
    val navController = rememberNavController()
    AppNav(
        navController,
        setFabAction = {},
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AppNavBar(navController: NavHostController) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    val isShown = Route.Main.matchesNavDestination(currentBackStackEntry?.destination)

    AnimatedVisibility(
        isShown,
        enter = slideInVertically { it },
        exit = slideOutVertically { it },
    ) {
        NavigationBar {
            BottomNavigationItem.entries.forEach { item ->
                val isSelected = item.matchesNavDestination(currentBackStackEntry?.destination)
                NavigationBarItem(isSelected, {
                    if (isSelected) {
                        return@NavigationBarItem
                    }

                    navController.navigate(item.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }, icon = {
                    Icon(
                        if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = "Bottom navigation icon"
                    )
                }, label = {
                    if (item.label.isNotBlank()) {
                        Text(
                            item.label,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                })
            }
        }
    }
}

@Composable
@Preview
fun AppNavBarPreview() {
    val navController = rememberNavController()
    AppNavBar(navController)
}