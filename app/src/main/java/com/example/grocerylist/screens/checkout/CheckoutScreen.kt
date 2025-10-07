package com.example.grocerylist.screens.checkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.grocerylist.KonfettiPresets
import com.example.grocerylist.Load
import com.example.grocerylist.screens.settings.checkout.CheckoutProgressCircular
import nl.dionsegijn.konfetti.compose.KonfettiView
import org.koin.androidx.compose.koinViewModel

@Composable
fun CheckoutScreenHolder(
    modifier: Modifier = Modifier,
    viewModel: CheckoutViewModel = koinViewModel(),
) {
    val loadingItems by viewModel.items.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.loadItems()
    }

    Load(
        loadingItems,
        loadingContent = { Text("Loading :)") },
    ) { items ->
        CheckoutScreen(
            items = items,
            onItemCheck = { idx, item, isChecked ->
                // TODO - use ID instead of idx
                viewModel.checkItemByIdx(idx, isChecked)
            },
            modifier = modifier,
        )
    }
}

@Composable
fun CheckoutScreen(
    items: List<CheckoutItem>,
    onItemCheck: (Int, CheckoutItem, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val progress = if (items.isNotEmpty()) {
                items.count { it.isChecked }.toFloat() / items.size.toFloat()
            } else {
                0f
            }

            CheckoutProgressCircular(progress)
            CheckoutItems(items, onItemCheck = onItemCheck)
        }

        // Show some nice confetti when completing the checkout list :)
        if (items.isNotEmpty() && items.all { it.isChecked }) {
            KonfettiView(
                parties = KonfettiPresets.festive()
            )
        }
    }
}

@Composable
@Preview
fun CheckoutScreenPreview() {
    CheckoutScreen(
        items = listOf(
            CheckoutItem("Banana", "Yellowish ripe"),
            CheckoutItem("Chocolate", "Tnoova brand"),
            CheckoutItem("Meat", "Without skin"),
        ),
        onItemCheck = { _, _, _ -> },
    )
}

