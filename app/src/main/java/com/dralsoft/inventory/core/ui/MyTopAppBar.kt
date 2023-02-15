package com.dralsoft.inventory.core.ui

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.dralsoft.inventory.R
import com.dralsoft.inventory.core.ViewConfig
import com.dralsoft.inventory.ui.theme.Purple700

@Composable
fun MyTopAppBar(viewConfig: ViewConfig = ViewConfig(), onClick: (String) -> Unit, onClickNavIcon: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = LocalContext.current.getString(R.string.app_name))

        },
        backgroundColor = Purple700, contentColor = Color.White,
        navigationIcon =
        if (viewConfig.showBackButton) {
            {
                IconButton(onClick = {
                    onClickNavIcon()
                }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
                }
            }
        } else {
            null
        }


        /*, actions = {
            IconButton(onClick = {
                onClick("Search")
            }) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "search")
            }

            IconButton(onClick = {
                onClick("Add")
            }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "add")
            }
        }*/
    )
}