package com.dralsoft.inventory.list.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dralsoft.inventory.core.ErrorView
import com.dralsoft.inventory.core.Loading
import com.dralsoft.inventory.core.ScaffoldView
import com.dralsoft.inventory.core.ui.UiState
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
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp)

    ) {
        items(state.data.data) {
            Text(text = "${it.attributes.name} (${it.attributes.status})")
        }
    }
}

