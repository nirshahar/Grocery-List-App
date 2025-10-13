@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.grocerylist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.grocerylist.navigation.Route
import com.example.grocerylist.navigation.SCREENS
import com.example.grocerylist.navigation.Screen
import com.example.grocerylist.navigation.getCurrentScreen
import com.example.grocerylist.screens.edit.EditViewModel
import com.example.grocerylist.ui.data.Product
import org.koin.androidx.compose.koinViewModel

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
                    Route.Checkout -> "Checkout"
                    Route.Main.Edit -> "Edit"
                    Route.Main.Home -> "Home"
                    Route.Main.Settings -> "Settings"
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

@Composable
fun EditTopBarActionsHolder(viewModel: EditViewModel = koinViewModel()) {
    val loadingItems by viewModel.items.collectAsState()

    Load(loadingItems, loadingContent = {}) { items ->
        val selectedItems = items.filter { it.isSelected }

        EditTopBarActions(
            selectedItems,
            onDeleteClick = viewModel::removeItems,
            onEditClick = viewModel::requestEditItem
        )
    }
}

@Composable
fun EditTopBarActions(
    selectedItems: List<Product>,
    onDeleteClick: (itemsToDelete: List<Product>) -> Unit,
    onEditClick: (editItem: Product) -> Unit
) {
    if (selectedItems.size == 1) {
        IconButton(onClick = { onEditClick(selectedItems.single()) }) {
            Icon(Icons.Default.Edit, "Edit selected item")
        }
    }

    if (selectedItems.isNotEmpty()) {
        IconButton(onClick = { onDeleteClick(selectedItems) }) {
            Icon(Icons.Default.Delete, "Delete selected items")
        }
    }
}

@Preview
@Composable
private fun TopAppBarActionsPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(32.dp)) {
        for (screen in SCREENS) {
            Row {
                TopAppBarActions(screen)
            }
        }
    }
}