package com.example.grocerylist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.grocerylist.navigation.BottomNavigationItem

typealias SetFabAction = (() -> Unit) -> Unit

@Composable
fun AppFab(navController: NavHostController, fabAction: () -> Unit) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    val currentDestination = BottomNavigationItem.entries.firstOrNull { item ->
        item.matchesNavDestination(
            currentBackStackEntry?.destination
        )
    }

    AppFabContent(fabAction, currentDestination)
}

@Composable
fun AppFabContent(fabAction: () -> Unit, currentDestination: BottomNavigationItem? = null) {
    AnimateFabVisibility(currentDestination == BottomNavigationItem.Home) {
        StartCheckoutFab(onClick = fabAction)
    }

    AnimateFabVisibility(currentDestination == BottomNavigationItem.Edit) {
        AddItemFab(onClick = fabAction)
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
@Composable
fun AddItemFab(onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Icon(Icons.Default.Add, "Add a new item")
    }
}

@Composable
fun StartCheckoutFab(onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Icon(Icons.Default.PlayArrow, "Start a new checkout")
    }
}

@Preview(showBackground = true)
@Composable
private fun AppFabPreview() {
    Row(horizontalArrangement = Arrangement.spacedBy(32.dp)) {
        BottomNavigationItem.entries.forEach {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(it.label)
                AppFabContent(fabAction = {}, currentDestination = it)
            }
        }
    }
}