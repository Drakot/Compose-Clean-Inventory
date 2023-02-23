package com.dralsoft.inventory.core.ui

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun MyFab(imageVector: ImageVector? = Icons.Filled.Add, onFABClick: () -> Unit = {}) {
    FloatingActionButton(contentColor = Color.White, onClick = onFABClick) {
        if (imageVector != null) {
            Icon(imageVector = imageVector, contentDescription = "add")
        }
    }
}