@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.grocerylist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.grocerylist.navigation.AppNav
import com.example.grocerylist.navigation.AppNavBar
import com.example.grocerylist.ui.theme.GroceryListTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val mainActivityViewModel = koinViewModel<MainActivityViewModel>()
            LaunchedEffect(true) {
                mainActivityViewModel.optimizeDb()
            }

            GroceryListTheme {
                MainContent()
            }
        }
    }
}

val LocalSharedElementScope =
    compositionLocalOf<SharedTransitionScope> { error("Not in shared element scope") }

@Composable
@Preview
fun MainContent() {
    val navController = rememberNavController()

    val (fabAction, setFabAction) = remember { mutableStateOf({}) }

    Scaffold(
        bottomBar = {
            AppNavBar(navController)
        },
        topBar = {
            giveanameforthis(navController)
        },
        floatingActionButton = {
            AppFab(navController, fabAction = fabAction)
        }
    ) { innerPadding ->
        SharedTransitionLayout {
            CompositionLocalProvider(LocalSharedElementScope provides this) {
                AppNav(
                    navController = navController,
                    setFabAction,
                    modifier = Modifier
                        .padding(8.dp)
                        .padding(innerPadding)
                )
            }
        }
    }
}