package com.example.grocerylist.screens.edit

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.grocerylist.bottomSheet.AddItemBottomSheet
import com.example.grocerylist.Load
import com.example.grocerylist.LoadingState
import com.example.grocerylist.SetFabAction
import com.example.grocerylist.ui.data.Product
import org.koin.androidx.compose.koinViewModel

@Composable
fun EditScreenHolder(
    setFabAction: SetFabAction,
    modifier: Modifier = Modifier,
    viewModel: EditViewModel = koinViewModel()
) {
    val loadingItems by viewModel.items.collectAsStateWithLifecycle()
    val addItemBottomSheetState = viewModel.editItemBottomSheetData

    LaunchedEffect(true) {
        setFabAction {
            viewModel.showEditItemBottomSheet()
        }
    }

    EditScreen(
        loadingItems = loadingItems,
        addItemBottomSheetState = addItemBottomSheetState,
        selectItem = { idx, item, isSelected -> viewModel.selectItem(item.id, isSelected) },
        onDismissBottomSheet = { viewModel.dismissEditItemBottomSheet() },
        onAddItem = viewModel::addItem,
        swapItemsOrder = viewModel::swapItemsOrderSuspend,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
    loadingItems: LoadingState<List<Product>>,
    addItemBottomSheetState: Product?,
    selectItem: (idx: Int, item: Product, isSelected: Boolean) -> Unit,
    swapItemsOrder: suspend (firstItem: Product, secondItem: Product) -> Unit,
    onDismissBottomSheet: () -> Unit,
    onAddItem: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState()
    val bottomSheetScope = rememberCoroutineScope()


    Surface(modifier = modifier.fillMaxSize()) {
        // TODO - put real content here instead of dummy `CheckoutRow` view
        Load(
            loadingItems, loadingContent = {
                Text("Loading :)")
            }) { items ->
            EditProductRows(
                items,
                selectItem = selectItem,
                swapItemsOrder = swapItemsOrder,
            )
        }
    }

    if (addItemBottomSheetState != null) {
        AddItemBottomSheet(
            itemData = addItemBottomSheetState,
            scope = bottomSheetScope,
            sheetState = sheetState,
            onDismiss = onDismissBottomSheet,
            onSubmit = onAddItem
        )
    }
}


@Composable
@Preview
private fun EditScreenPreview() {
    EditScreen(
        loadingItems = LoadingState.Finished(
            listOf(
                Product("Banana", "Yellowish ripe"),
                Product("Chocolate", "Tnoova brand"),
                Product("Meat", "Without skin"),
            )
        ),
        addItemBottomSheetState = null,
        onAddItem = {},
        selectItem = { _, _, _ -> },
        swapItemsOrder = { _, _ -> },
        onDismissBottomSheet = {},
    )
}