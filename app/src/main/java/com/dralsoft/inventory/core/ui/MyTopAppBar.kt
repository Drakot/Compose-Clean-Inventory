package com.dralsoft.inventory.core.ui

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.dralsoft.inventory.R
import com.dralsoft.inventory.ui.theme.Purple700

@Composable
fun MyTopAppBar(onClick: (String) -> Unit, onClickDrawer: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = LocalContext.current.getString(R.string.app_name))

        },
        backgroundColor = Purple700, contentColor = Color.White,
        /* navigationIcon = {
             IconButton(onClick = {
                 onClickDrawer()
             }) {
                 Icon(imageVector = Icons.Filled.Menu, contentDescription = "back")
             }

         }*/
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