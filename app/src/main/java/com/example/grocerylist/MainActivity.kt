@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.grocerylist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.grocerylist.checkout.CheckoutViewModel
import com.example.grocerylist.navigation.AppNavBar
import com.example.grocerylist.navigation.AppNav
import com.example.grocerylist.ui.theme.GroceryListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainContent()
        }
    }
}

@Composable
@Preview
fun MainContent() {
    val checkoutViewModel: CheckoutViewModel = viewModel()
    val navController = rememberNavController()

    var showAddItemBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val bottomSheetScope = rememberCoroutineScope()

    GroceryListTheme {
        Scaffold(bottomBar = { AppNavBar(navController) }, floatingActionButton = {
            AppFab(navController, checkoutFabClick = { showAddItemBottomSheet = true })
        }) { innerPadding ->
            AppNav(
                navController = navController,
                checkoutViewModel = checkoutViewModel,
                modifier = Modifier
                    .padding(8.dp)
                    .padding(innerPadding)
            )

            if (showAddItemBottomSheet) {
                AddItemBottomSheet(
                    scope = bottomSheetScope,
                    sheetState = sheetState,
                    onDismiss = { showAddItemBottomSheet = false },
                    onSubmit = checkoutViewModel::addItem
                )
            }
        }
    }
}