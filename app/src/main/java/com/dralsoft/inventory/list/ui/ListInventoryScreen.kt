package com.dralsoft.inventory.list.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.dralsoft.inventory.core.Loading
import com.dralsoft.inventory.core.ScaffoldView
import com.dralsoft.inventory.core.ViewConfig
import com.dralsoft.inventory.core.ui.mvi.collectInLaunchedEffectWithLifecycle
import com.dralsoft.inventory.list.data.response.InventoryItem
import timber.log.Timber

@Composable
fun ListInventoryScreen(navController: NavController, viewModel: ListInventoryViewModel = hiltViewModel()) {
    val state = viewModel.viewState.collectAsState().value


    Box(modifier = Modifier.fillMaxSize()) {
        ScaffoldView(ViewConfig(fabImage = Icons.Filled.Add, onSearchClicked = {
            viewModel.submitIntent(ListIntent.OnSearchClicked)
        }, onFABClick = {
            viewModel.submitIntent(ListIntent.AddInventory)
        }),
            searchWidgetState = state.searchState,
            searchTextState = state.searchText,
            onTextChange = {
                viewModel.submitIntent(ListIntent.OnTypeSearch(it))
            },
            onCloseClicked = {
                viewModel.submitIntent(ListIntent.OnCloseSearchClick)
            },
            onSearch = {
                viewModel.submitIntent(ListIntent.OnSearch(it))
            }) {
            Loading(state.isLoading)

            OnSuccess(state, {
                viewModel.submitIntent(ListIntent.InventoryClick(it.id))
            }, {
                Timber.tag("ListInventoryScreen").i("Long click")
            })

            if (state.data.isEmpty() && !state.isLoading) {
                Text(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center),
                    text = LocalContext.current.getString(com.dralsoft.inventory.R.string.list_inventory_empty)
                )
            }
        }
    }

    viewModel.singleEvent.collectInLaunchedEffectWithLifecycle { event ->
        when (event) {
            is ListUiSingleEvent.Error -> {

            }
            is ListUiSingleEvent.OpenDetailScreen -> {
                navController.navigate(event.navRoute)
            }
        }
    }
}

@Composable
fun OnSuccess(
    state: ListInventoryState,
    onItemSelected: (InventoryItem) -> Unit,
    onItemLongSelected: (InventoryItem) -> Unit
) {
    LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
        items(items = state.data) {
            InventoryItemView(it, onItemSelected, onItemLongSelected)
        }
    }, contentPadding = PaddingValues(0.dp))
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InventoryItemView(
    inventoryItem: InventoryItem,
    onItemSelected: (InventoryItem) -> Unit,
    onItemLongSelected: (InventoryItem) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .combinedClickable(
                onClick = { onItemSelected(inventoryItem) },
                onLongClick = { onItemLongSelected(inventoryItem) },
            )
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(130.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop,
            model = inventoryItem.attributes.pictures.firstOrNull(),
            contentDescription = "Translated description of what the image contains"
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 16.sp,
            style = MaterialTheme.typography.h3,
            text = "${inventoryItem.attributes.name} (${inventoryItem.attributes.status})"
        )
    }

}


