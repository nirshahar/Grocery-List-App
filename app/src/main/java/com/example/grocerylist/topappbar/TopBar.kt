@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.grocerylist.topappbar

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.grocerylist.R
import com.example.grocerylist.navigation.Route
import com.example.grocerylist.navigation.Screen
import com.example.grocerylist.navigation.getCurrentScreen

@Composable
fun MyTopAppBar(navController: NavHostController, modifier: Modifier = Modifier) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = navBackStackEntry?.destination?.getCurrentScreen() ?: return

    // Currently, only edit screen has top bar
    val isShown = when (currentScreen) {
        Route.Main.Edit -> true
        Route.Checkout -> false
        Route.Main.Home -> false
        Route.Main.Settings -> false
    }

    if (isShown) {
        CenterAlignedTopAppBar(
            title = {
                val text = when (currentScreen) {
                    Route.Checkout -> stringResource(R.string.checkout_title)
                    Route.Main.Edit -> stringResource(R.string.edit_title)
                    Route.Main.Home -> stringResource(R.string.home_title)
                    Route.Main.Settings -> stringResource(R.string.settings_title)
                }

                Text(text)
            },
            actions = {
                // The navigation stack has the correct lifecycle owner for the screen's viewmodel
                val viewModelStoreOwner = navBackStackEntry ?: return@CenterAlignedTopAppBar
                CompositionLocalProvider(LocalViewModelStoreOwner provides viewModelStoreOwner) {
                    TopAppBarActions(currentScreen)
                }
            },
            modifier = modifier,
        )
    }
}

@Composable
fun TopAppBarActions(screen: Screen) {
    when (screen) {
        Route.Checkout -> {}
        Route.Main.Edit -> {
            EditTopBarActionsHolder()
        }

        Route.Main.Home -> {}
        Route.Main.Settings -> {}
    }
}