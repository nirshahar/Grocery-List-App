package com.example.grocerylist.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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

val LocalNavAnimatedScope = compositionLocalOf<AnimatedContentScope> { error("Not in nav scope") }


inline fun NavGraphBuilder.appComposable(
    route: Route,
    noinline content: @Composable AnimatedContentScope.() -> Unit,
) {
    composable(route::class) {
        CompositionLocalProvider(LocalNavAnimatedScope provides this) {
            content()
        }
    }
}

inline fun <reified T : Any> NavGraphBuilder.appComposable(noinline content: @Composable AnimatedContentScope.() -> Unit) {
    composable<T> {
        CompositionLocalProvider(LocalNavAnimatedScope provides this) {
            content()
        }
    }
}

@Composable
fun AppNav(
    navController: NavHostController,
    setFabAction: SetFabAction,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Route.Main,
        modifier = modifier,
    ) {
        appComposable<Route.Checkout> {
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
        appComposable(item.route) {
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
                        popUpTo(Route.Main)
                    }
                }, icon = {
                    Icon(
                        if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = "Bottom navigation icon"
                    )
                }, label = {
                    Text(
                        stringResource(item.label),
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
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