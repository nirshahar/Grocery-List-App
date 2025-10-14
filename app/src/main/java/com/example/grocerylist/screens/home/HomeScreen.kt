package com.example.grocerylist.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.grocerylist.SetFabAction
import com.example.grocerylist.navigation.Route

@Composable
fun HomeScreenHolder(
    navController: NavHostController,
    setFabAction: SetFabAction,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(true) {
        setFabAction {
            navController.navigate(Route.Checkout)
        }
    }

    // TODO - add viewmodel, fab, etc...
    HomeScreen(
        modifier = modifier
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    Surface(modifier = modifier.fillMaxSize()) {
        LazyVerticalGrid(
            GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(4) {
                Card(modifier = Modifier.size(200.dp)) {
//                    Text("Example Blueprint $it")
                }
            }
        }
        // TODO - add content (i.e, welcome XYZ, list of blueprints, recent statistics)
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}