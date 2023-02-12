package com.dralsoft.inventory.list.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.dralsoft.inventory.core.ErrorView
import com.dralsoft.inventory.core.Loading
import com.dralsoft.inventory.core.ScaffoldView
import com.dralsoft.inventory.core.ui.UiState
import com.dralsoft.inventory.list.data.response.InventoryItem
import com.dralsoft.inventory.list.data.response.ListInventoryResponse
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ListInventoryScreen(navController: NavController, viewModel: ListInventoryViewModel = hiltViewModel()) {

    val state = viewModel.uiStateFlow.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.submitAction(ListUiAction.Load)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        ScaffoldView{
            when (state) {
                is UiState.Loading -> {
                    Loading()
                }
                is UiState.Error -> {
                    ErrorView(state.errorMessage)
                }
                is UiState.Success -> {
                    OnSuccess(state)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.singleEventFlow.collectLatest {
            when (it) {
                is ListUiSingleEvent.OpenDetailScreen -> {
                    navController.navigate(it.navRoute)
                }
            }
        }
    }

}


@Composable
fun OnSuccess(state: UiState.Success<ListInventoryResponse>) {


    LazyVerticalGrid(columns = GridCells.Adaptive(100.dp), content = {
        items(items = state.data.data) {
            InventoryItemView(it)
        }
    }, contentPadding = PaddingValues(8.dp))
}

@Composable
fun InventoryItemView(inventoryItem: InventoryItem){
    Column(){
        AsyncImage(
            modifier = Modifier.width(100.dp).height(100.dp),
            model = inventoryItem.attributes.pictures.firstOrNull(),
            contentDescription = "Translated description of what the image contains"
        )
        Text(text = "${inventoryItem.attributes.name} (${inventoryItem.attributes.status})")
    }

}


