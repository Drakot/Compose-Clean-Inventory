package com.dralsoft.inventory.core.ui

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun MyFab() {
    FloatingActionButton(contentColor = Color.White, backgroundColor = Color.Blue, onClick = {

    }) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "add")
    }
}