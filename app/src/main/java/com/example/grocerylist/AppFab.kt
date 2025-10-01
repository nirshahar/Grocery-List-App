package com.example.grocerylist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.grocerylist.checkout.CheckoutAddItemFab
import com.example.grocerylist.navigation.BottomNavigationItem

@Composable
fun AppFab(navController: NavHostController, checkoutFabClick: () -> Unit) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    val currentDestination = BottomNavigationItem.entries.firstOrNull { item ->
        item.matchesNavDestination(
            currentBackStackEntry?.destination
        )
    } ?: return

    AnimateFabVisibility(currentDestination == BottomNavigationItem.Checkout) {
        CheckoutAddItemFab(onClick = checkoutFabClick)
    }
}

@Composable
private fun AnimateFabVisibility(
    visible: Boolean,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = scaleIn(),
        exit = scaleOut(),
        content = content
    )
}