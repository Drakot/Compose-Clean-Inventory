package com.dralsoft.inventory.detail.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dralsoft.inventory.core.ErrorView
import com.dralsoft.inventory.core.Loading
import com.dralsoft.inventory.core.ScaffoldView
import com.dralsoft.inventory.core.navigation.InventoryItemInput
import com.dralsoft.inventory.core.ui.UiState
import com.dralsoft.inventory.detail.data.response.InventoryResponse
import kotlinx.coroutines.flow.collectLatest

@Composable
fun InventoryScreen(
    navController: NavController,
    inventoryItemInput: InventoryItemInput,
    viewModel: InventoryViewModel = hiltViewModel()
) {
    val state = viewModel.uiStateFlow.collectAsState().value

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
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.singleEventFlow.collectLatest {
          /*  when (it) {
                is ListUiSingleEvent.OpenDetailScreen -> {
                    navController.navigate(it.navRoute)
                }
            }*/
        }
    }

}

@Composable
fun OnSuccess(state: UiState.Success<InventoryResponse>) {
    Text(text = "weee")
}


