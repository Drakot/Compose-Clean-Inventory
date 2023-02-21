package com.dralsoft.inventory.detail.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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

@Composable
fun BottomSheetItems(
    text: String,
    icon: ImageVector,
    tint: Color = MaterialTheme.colors.primaryVariant,
    onClick: () -> Unit
) {
    TextButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "",
                tint = tint,
                modifier = Modifier
                    .padding(end = 12.dp)
                    .align(Alignment.CenterVertically)
                    .size(30.dp),
            )

            Text(
                text = text,
                modifier = Modifier
                    .padding(end = 12.dp)
                    .align(Alignment.CenterVertically),
                style = MaterialTheme.typography.h1,
                color = Color.Black,
                fontSize = 20.sp
            )
        }

    }
}