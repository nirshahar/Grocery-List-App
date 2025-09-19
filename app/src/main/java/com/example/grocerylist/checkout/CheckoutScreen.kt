package com.example.grocerylist.checkout

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastCoerceIn
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CheckoutScreenHolder(
    modifier: Modifier = Modifier,
    viewModel: CheckoutViewModel = viewModel()
) {
    val items by viewModel.items.collectAsState()

    LaunchedEffect(viewModel) {
        // TEMPORARY TODO
        viewModel.addItem(CheckoutItem("Banana", "Yellowish ripe"))
        viewModel.addItem(CheckoutItem("Chocolate", "Tnoova brand"))
        viewModel.addItem(CheckoutItem("Meat", "Without skin"))
    }

    CheckoutScreen(items, modifier) { idx, item, isChecked ->
        viewModel.checkItem(idx, isChecked)
    }
}

@Composable
fun CheckoutScreen(
    items: List<CheckoutItem>,
    modifier: Modifier = Modifier,
    onItemCheck: (Int, CheckoutItem, Boolean) -> Unit
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val progress = if (items.isNotEmpty()) {
                items.count { it.isChecked }.toFloat() / items.size.toFloat()
            } else {
                1f
            }.fastCoerceIn(0f, 1f)

            CheckoutProgress(progress)
            CheckoutItems(items, onItemCheck = onItemCheck)
        }
    }
}

@Composable
fun CheckoutItems(
    items: List<CheckoutItem>,
    modifier: Modifier = Modifier,
    onItemCheck: (idx: Int, item: CheckoutItem, isChecked: Boolean) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(items.size) { idx ->
            CheckoutRow(items[idx]) { isChecked ->
                onItemCheck(idx, items[idx], isChecked)
            }
        }
    }
}

@Composable
fun CheckoutProgress(progress: Float, modifier: Modifier = Modifier) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            "Progress",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            Text(
                "${(progress * 100).toInt()}%",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Composable
fun CheckoutRow(item: CheckoutItem, modifier: Modifier = Modifier, onCheck: (Boolean) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onCheck(!item.isChecked)
            }
            .padding(4.dp)
    ) {
        Checkbox(item.isChecked, onCheckedChange = null)
        Column {
            Text(item.itemName, style = MaterialTheme.typography.titleMedium)
            Text(
                item.subtext,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
@Preview
fun CheckoutScreenPreview() {
    CheckoutScreen(
        listOf(
            CheckoutItem("Banana", "Yellowish ripe"),
            CheckoutItem("Chocolate", "Tnoova brand"),
            CheckoutItem("Meat", "Without skin"),
        ),
    ) { _, _, _ -> }
}

@Composable
@Preview(showBackground = true)
fun CheckoutRowPreview() {
    CheckoutRow(
        CheckoutItem("Banana", "Yellowish ripe"),
    ) { }
}

@Preview(showBackground = true)
@Composable
private fun CheckoutProgressPreview() {
    CheckoutProgress(0.5f)
}