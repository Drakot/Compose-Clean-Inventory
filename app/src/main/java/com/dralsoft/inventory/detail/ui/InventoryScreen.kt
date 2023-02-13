package com.dralsoft.inventory.detail.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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


    viewModel.submitAction(InventoryUiAction.Load(inventoryItemInput.id))


    Box(modifier = Modifier.fillMaxSize()) {
        ScaffoldView {
            val modifier = Modifier.fillMaxWidth()
            Column() {
                MyTextFieldOutlined(
                    modifier
                        .align(Alignment.CenterHorizontally)
                )
            }


            when (state) {
                is UiState.Loading -> {
                    Loading()
                }
                is UiState.Error -> {
                    ErrorView(state.errorMessage)
                }
                is UiState.Success -> {
                    /*   Column() {
                           Text(text = "weee")
                           Button(onClick = { }) {
                               Text(text = "Button")
                           }
                       }*/
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

}


@Composable
fun MyTextFieldOutlined(modifier: Modifier) {
    var myText by remember { mutableStateOf("") }
    OutlinedTextField(
        value = myText,
        onValueChange = {
            myText = it
        },
        modifier = modifier
            .padding(16.dp),
        label = { Text(text = "Introduce tu nombre") },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            //   focusedBorderColor = Color.Magenta, unfocusedBorderColor = Color.Red
        )
    )
}

