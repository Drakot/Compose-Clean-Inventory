package com.dralsoft.inventory.core

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dralsoft.inventory.core.ui.MyFab
import com.dralsoft.inventory.core.ui.MyTopAppBar
import kotlinx.coroutines.launch

@Composable
fun ErrorView(errorMessage: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        Snackbar {
            Text(text = errorMessage)
        }
    }
}

@Composable
fun Loading(isLoading: Boolean) {
    AnimatedVisibility(
        modifier = Modifier.fillMaxSize(),
        visible = isLoading,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CircularProgressIndicator()
        }
    }
}

data class ViewConfig(val showFAB: Boolean = false, val showBackButton: Boolean = false)

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldView(
    viewConfig: ViewConfig = ViewConfig(),
    onClickNavIcon: () -> Unit = {},
    onFABClick: () -> Unit = {},
    Content: @Composable () -> Unit,
    ) {
    val state = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            MyTopAppBar(viewConfig, onClick = {
                coroutineScope.launch {
                    state.snackbarHostState.showSnackbar(it)
                }
            }, onClickNavIcon = onClickNavIcon)
        }, scaffoldState = state,

        floatingActionButton = {
            if (viewConfig.showFAB) {
                MyFab(onFABClick)
            }
        }
    ) {
        Content()
    }
}