package com.dralsoft.inventory.core

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dralsoft.inventory.core.ui.MyTopAppBar
import com.dralsoft.inventory.core.ui.MyFab
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
fun Loading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator()
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldView(Content: @Composable () -> Unit) {
    val state = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            MyTopAppBar(onClick = {
                coroutineScope.launch {
                    state.snackbarHostState.showSnackbar(it)
                }
            }, onClickDrawer = {
                coroutineScope.launch {
                    state.drawerState.open()
                }
            })
        }, scaffoldState = state,

        floatingActionButton = {
            MyFab()
        }
    ) {
        Content()
    }
}