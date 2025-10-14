@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.grocerylist.bottomSheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.grocerylist.R
import com.example.grocerylist.screens.edit.EditItemBottomSheetData
import com.example.grocerylist.ui.data.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AddItemBottomSheet(
    data: EditItemBottomSheetData.Shown,
    scope: CoroutineScope,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onSubmit: (Product) -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        AddItemBottomSheetContent(data.item) { item ->
            onSubmit(item)

            scope.launch {
                sheetState.hide()
            }.invokeOnCompletion {
                if (!sheetState.isVisible) {
                    onDismiss()
                }
            }
        }
    }
}

@Composable
fun AddItemBottomSheetContent(itemData: Product, onSubmit: (Product) -> Unit) {
    var name by remember(itemData) { mutableStateOf(itemData.name) }
    var description by remember(itemData) { mutableStateOf(itemData.description) }

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(stringResource(R.string.add_item_name_label)) },
            placeholder = { Text(stringResource(R.string.add_item_name_placeholder)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { newDescription ->
                if (newDescription.lines().size <= 3) {
                    description = newDescription
                }
            },
            label = { Text(stringResource(R.string.add_item_description_label)) },
            placeholder = { Text(stringResource(R.string.add_item_description_placeholder)) },
            maxLines = 3,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            onSubmit(itemData.copy(name = name, description = description))
        }) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Done, contentDescription = null)
                Text(stringResource(R.string.add_item_save_button))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AddItemBottomSheetContentPreview() {
    AddItemBottomSheetContent(Product("", "")) {}
}