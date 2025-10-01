@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.grocerylist

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
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.grocerylist.checkout.CheckoutItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AddItemBottomSheet(
    scope: CoroutineScope,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onSubmit: (CheckoutItem) -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        AddItemBottomSheetContent { item ->
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
fun AddItemBottomSheetContent(onSubmit: (CheckoutItem) -> Unit) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Item name") },
            placeholder = { Text("Banana") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(4.dp))

        TextField(
            value = description,
            onValueChange = { newDescription ->
                if (newDescription.lines().size <= 3) {
                    description = newDescription
                }
            },
            label = { Text("Optional Description") },
            placeholder = { Text("Ripe with black dots") },
            maxLines = 3,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            onSubmit(CheckoutItem(name, description))
        }) {
            Row {
                Icon(Icons.Default.Done, contentDescription = null)
                Text("Save")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AddItemBottomSheetContentPreview() {
    AddItemBottomSheetContent {}
}