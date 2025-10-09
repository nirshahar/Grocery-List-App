package com.example.grocerylist.screens.edit

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.grocerylist.AddItemBottomSheet
import com.example.grocerylist.Load
import com.example.grocerylist.LoadingState
import com.example.grocerylist.SetFabAction
import com.example.grocerylist.screens.checkout.CheckoutItem
import com.example.grocerylist.screens.checkout.CheckoutRow
import org.koin.androidx.compose.koinViewModel

@Composable
fun EditScreenHolder(
    setFabAction: SetFabAction,
    modifier: Modifier = Modifier,
    viewModel: EditViewModel = koinViewModel()
) {
    val loadingItems by viewModel.items.collectAsState()

    EditScreen(
        loadingItems = loadingItems,
        selectItem = { idx, item, isSelected -> viewModel.selectItem(item.id, isSelected) },
        setFabAction = setFabAction,
        onAddItem = viewModel::addItem,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
    loadingItems: LoadingState<List<CheckoutItem>>,
    selectItem: (Int, CheckoutItem, Boolean) -> Unit,
    setFabAction: SetFabAction,
    onAddItem: (CheckoutItem) -> Unit,
    modifier: Modifier = Modifier
) {
    var showAddItemBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val bottomSheetScope = rememberCoroutineScope()

    LaunchedEffect(true) {
        setFabAction {
            showAddItemBottomSheet = true
        }
    }

    Surface(modifier = modifier.fillMaxSize()) {
        // TODO - put real content here instead of dummy `CheckoutRow` view
        Load(
            loadingItems, loadingContent = {
                Text("Loading :)")
            }) { items ->
            EditItems(items, selectItem = selectItem)
        }
    }

    if (showAddItemBottomSheet) {
        AddItemBottomSheet(
            scope = bottomSheetScope,
            sheetState = sheetState,
            onDismiss = { showAddItemBottomSheet = false },
            onSubmit = onAddItem
        )
    }
}

@Composable
fun EditItems(
    items: List<CheckoutItem>,
    selectItem: (Int, CheckoutItem, Boolean) -> Unit
) {
    LazyColumn {
        items(items.size) { idx ->
            CheckoutRow(items[idx]) {
                selectItem(idx, items[idx], it)
            }
        }
    }
}

@Preview
@Composable
private fun EditItemsPreview() {
    EditItems(
        items = listOf(
            CheckoutItem("Banana", "Yellowish ripe"),
            CheckoutItem("Chocolate", "Tnoova brand"),
            CheckoutItem("Meat", "Without skin"),
        ),
        { _, _, _ -> }
    )
}

@Composable
@Preview
private fun EditScreenPreview() {
    EditScreen(
        loadingItems = LoadingState.Finished(
            listOf(
                CheckoutItem("Banana", "Yellowish ripe"),
                CheckoutItem("Chocolate", "Tnoova brand"),
                CheckoutItem("Meat", "Without skin"),
            )
        ),
        setFabAction = {},
        onAddItem = {},
        selectItem = { _, _, _ -> },
    )
}