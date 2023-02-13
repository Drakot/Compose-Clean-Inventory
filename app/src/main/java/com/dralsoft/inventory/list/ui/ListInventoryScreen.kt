package com.dralsoft.inventory.list.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

    //viewModel.submitAction(ListUiAction.Load)

    Box(modifier = Modifier.fillMaxSize()) {
        ScaffoldView {
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
                UiState.Empty -> {
                    Text(text = LocalContext.current.getString(com.dralsoft.inventory.R.string.list_inventory_empty))
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


    LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
        items(items = state.data.data) {
            InventoryItemView(it)
        }
    }, contentPadding = PaddingValues(0.dp))
}

@Composable
fun InventoryItemView(inventoryItem: InventoryItem) {
    Column(modifier = Modifier.padding(8.dp)) {
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


