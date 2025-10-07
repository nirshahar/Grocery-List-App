package com.example.grocerylist.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.grocerylist.fabs.SetFabAction
import com.example.grocerylist.navigation.Route

@Composable
fun HomeScreenHolder(navController: NavController, setFabAction: SetFabAction, modifier: Modifier = Modifier) {
    // TODO - add viewmodel, fab, etc...
    HomeScreen(navController = navController, setFabAction = setFabAction, modifier = modifier)
}

@Composable
fun HomeScreen(navController: NavController, setFabAction: SetFabAction, modifier: Modifier = Modifier) {
    LaunchedEffect(true) {
        setFabAction {
            navController.navigate(Route.Checkout)
        }
    }

    Surface(modifier = modifier.fillMaxSize()) {
        // TODO - add content (i.e, welcome XYZ, list of blueprints, recent statistics)
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(
        navController,
        setFabAction = {},
    )
}