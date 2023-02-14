package com.dralsoft.inventory.detail.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.data.EmptyGroup.name
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dralsoft.inventory.core.ErrorView
import com.dralsoft.inventory.core.Loading
import com.dralsoft.inventory.core.ScaffoldView
import com.dralsoft.inventory.core.navigation.InventoryItemInput
import com.dralsoft.inventory.core.ui.MySpacer
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
            Form(viewModel)

            when (state) {
                is UiState.Loading -> {
                    Loading()
                }
                is UiState.Error -> {
                    ErrorView(state.errorMessage)
                }
                is UiState.Success -> {

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
fun Form(viewModel: InventoryViewModel) {
    val context = LocalContext.current
    val modifier = Modifier.fillMaxWidth()

    Column(modifier = Modifier.padding(16.dp)) {

        Name(
            name.value,
            TextTypeInfo(context.getString(com.dralsoft.inventory.R.string.name), KeyboardType.Text),
            modifier.align(Alignment.CenterHorizontally)
        ) {
            name.value = it
            viewModel.submitAction(InventoryUiAction.NameChanged(it))
        }
        MySpacer(16)

        Name(
            desc.value,
            TextTypeInfo(context.getString(com.dralsoft.inventory.R.string.desc), KeyboardType.Text),
            modifier.align(Alignment.CenterHorizontally)
        ) {
            viewModel.submitAction(InventoryUiAction.DescChanged(it))
        }
        MySpacer(16)

        Name(
            "",
            TextTypeInfo(context.getString(com.dralsoft.inventory.R.string.amount), KeyboardType.Number),
            modifier.align(Alignment.CenterHorizontally)
        ) {
            viewModel.submitAction(InventoryUiAction.AmountChanged(it.toInt()))
        }

        MySpacer(16)

        Button(modifier = modifier.height(55.dp), onClick = { }) {
            Text(text = context.getString(com.dralsoft.inventory.R.string.save))
        }
    }
}


@Composable
fun Name(text: String, info: TextTypeInfo, modifier: Modifier, onTextChange: (String) -> Unit) {

    OutlinedTextField(
        value = text, onValueChange = { onTextChange(it) },
        modifier = modifier,
        placeholder = {
            Text(text = info.text)
        },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = info.type),
        /* colors = TextFieldDefaults.textFieldColors(
             backgroundColor = Color(0xFFFaFaFa),
             focusedIndicatorColor = Color.Transparent,
             unfocusedIndicatorColor = Color.Transparent,
             cursorColor = Color.Black,
             textColor = Color(0xFFB2B2B2)
         )*/
    )
}

data class TextTypeInfo(val text: String, val type: KeyboardType)
