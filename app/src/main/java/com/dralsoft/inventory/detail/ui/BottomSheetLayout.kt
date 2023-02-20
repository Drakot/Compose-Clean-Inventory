package com.dralsoft.inventory.detail.ui

import androidx.compose.material.*
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetLayout(modalSheetState: ModalBottomSheetState, Content: @Composable () -> Unit) {
    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetContent = {
            Content()
        }
    ) {

    }
}