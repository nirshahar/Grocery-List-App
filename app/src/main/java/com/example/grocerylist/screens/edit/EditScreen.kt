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
import com.example.grocerylist.fabs.SetFabAction
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

    Load(
        loadingItems,
        loadingContent = {
            Text("Loading :)")
        }
    ) { items ->
        EditScreen(
            items,
            setFabAction = setFabAction,
            onAddItem = viewModel::addItem,
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
    items: List<CheckoutItem>,
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
        LazyColumn {
            items(items.size) { idx ->
                CheckoutRow(items[idx]) { }
            }
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
@Preview
private fun EditScreenPreview() {
    EditScreen(
        emptyList(),
        {},
        {}
    )
}