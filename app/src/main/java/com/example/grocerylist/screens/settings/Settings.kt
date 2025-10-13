package com.example.grocerylist.screens.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SettingsScreenHolder(
    modifier: Modifier = Modifier) {
    // TODO - add viewmodel, etc...
    SettingsScreen(modifier = modifier)
}
@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    Surface(modifier = modifier.fillMaxSize()) {
        // TODO - add settings content
    }
}

@Composable
@Preview
fun SettingsScreenPreview() {
    SettingsScreen()
}