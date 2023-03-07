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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.dralsoft.inventory.core.ui.MyFab
import com.dralsoft.inventory.core.ui.MyTopAppBar
import com.dralsoft.inventory.core.ui.SearchWidgetState

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

data class ViewConfig(
    val showBackButton: Boolean = false,
    val onClick: (String) -> Unit = {},
    val onClickNavIcon: () -> Unit = {},
    val onSearchClicked: (() -> Unit)? = null,
    val onFABClick: () -> Unit = {},
    val fabImage: ImageVector? = null,
    val fabIsVisible: Boolean = false,
)

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldView(
    viewConfig: ViewConfig = ViewConfig(),
    searchWidgetState: SearchWidgetState = SearchWidgetState.CLOSED,
    searchTextState: String = "",
    onTextChange: (String) -> Unit = {},
    onCloseClicked: () -> Unit = {},
    onSearch: (String) -> Unit = {},
    Content: @Composable () -> Unit
) {
    val state = rememberScaffoldState()
    Scaffold(
        topBar = {
            MyTopAppBar(
                viewConfig,
                searchWidgetState = searchWidgetState,
                searchTextState = searchTextState,
                onTextChange = onTextChange,
                onCloseClicked = onCloseClicked,
                onSearch = onSearch,
            )
        }, scaffoldState = state,

        floatingActionButton = {
            viewConfig.fabImage?.let {
                MyFab(viewConfig.fabIsVisible, viewConfig.fabImage, onFABClick = viewConfig.onFABClick)
            }
        }
    ) {
        Content()
    }
}