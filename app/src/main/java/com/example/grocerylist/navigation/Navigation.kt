package com.example.grocerylist.navigation

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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.grocerylist.FabActionSetter
import com.example.grocerylist.screens.checkout.CheckoutScreenHolder
import com.example.grocerylist.screens.edit.EditScreenHolder
import com.example.grocerylist.screens.settings.SettingsScreenPreview

@Composable
fun AppNav(
    navController: NavHostController,
    setFabAction: FabActionSetter,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController, startDestination = Route.Checkout, modifier = modifier
    ) {
        BottomNavigationItem.entries.forEach { item ->
            composable(item.route::class) {
                when (item) {
                    BottomNavigationItem.Checkout -> {
                        CheckoutScreenHolder()
                    }

                    BottomNavigationItem.Edit -> {
                        EditScreenHolder(setFabAction)
                    }

                    BottomNavigationItem.Settings -> {
                        SettingsScreenPreview()
                    }

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