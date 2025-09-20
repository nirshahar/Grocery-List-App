package com.example.grocerylist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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
    val navController = rememberNavController()

    val checkoutViewModel: CheckoutViewModel = viewModel()

    GroceryListTheme {
        Scaffold(
            bottomBar = { AppNavBar(navController) },
            floatingActionButton = {
                AppFabHolder(navController, checkoutViewModel = checkoutViewModel)
            }) { innerPadding ->
            AppNav(
                navController = navController,
                checkoutViewModel = checkoutViewModel,
                modifier = Modifier
                    .padding(8.dp)
                    .padding(innerPadding)
            )
        }
    }
}