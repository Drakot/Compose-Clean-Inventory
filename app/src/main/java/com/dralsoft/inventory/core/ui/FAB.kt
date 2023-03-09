package com.dralsoft.inventory.core.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun MyFab(isVisible: Boolean = false, imageVector: ImageVector? = Icons.Filled.Add, onFABClick: () -> Unit = {}) {
    val density = LocalDensity.current
    AnimatedVisibility(
        //modifier = modifier,
        visible = isVisible,
        enter = slideInVertically {
            with(density) { 40.dp.roundToPx() }
        } + fadeIn(),
        exit = fadeOut(
            animationSpec = keyframes {
                this.durationMillis = 120
            }
        )
    ) {
        FloatingActionButton(contentColor = Color.White, onClick = onFABClick) {
            if (imageVector != null) {
                Icon(imageVector = imageVector, contentDescription = "add")
            }
        }
    }
}