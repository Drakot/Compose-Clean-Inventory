package com.dralsoft.inventory.detail.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dralsoft.inventory.R
import com.dralsoft.inventory.core.ScaffoldView
import com.dralsoft.inventory.core.ViewConfig
import com.dralsoft.inventory.core.navigation.InventoryItemInput
import com.dralsoft.inventory.core.ui.MySpacer
import com.dralsoft.inventory.core.ui.mvi.collectInLaunchedEffectWithLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun InventoryScreen(
    navController: NavController,
    inventoryItemInput: InventoryItemInput? = null,
    viewModel: InventoryViewModel = hiltViewModel()
) {

    viewModel.submitIntent(InventoryIntent.Load(inventoryItemInput?.id))

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state = rememberScaffoldState()

    viewModel.singleEvent.collectInLaunchedEffectWithLifecycle { event ->
        when (event) {
            is InventorySingleEvent.Error -> {
                scope.launch {
                    state.snackbarHostState.showSnackbar(
                        event.errorMessage
                    )
                }
            }
            is InventorySingleEvent.OnLoadSuccess -> {

            }
            is InventorySingleEvent.OnSaveSuccess -> {
                scope.launch {
                    state.snackbarHostState.showSnackbar(
                        context.getString(R.string.added_inventory)
                    )
                }
                scope.launch {
                    delay(200)
                    navController.popBackStack()
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        ScaffoldView(ViewConfig(showBackButton = true), onClickNavIcon = {
            navController.popBackStack()
        }) {
            Form(viewModel)
        }
    }
}

@Composable
fun Form(viewModel: InventoryViewModel) {
    val state = viewModel.viewState.collectAsState().value
    val context = LocalContext.current
    val modifier = Modifier.fillMaxWidth()

    Column(modifier = Modifier.padding(16.dp)) {

        Field(
            state.name,
            TextTypeInfo(context.getString(com.dralsoft.inventory.R.string.name), KeyboardType.Text),
            modifier.align(Alignment.CenterHorizontally)
        ) {
            viewModel.submitIntent(InventoryIntent.NameChanged(it))
        }
        MySpacer(16)

        Field(
            state.description,
            TextTypeInfo(context.getString(com.dralsoft.inventory.R.string.desc), KeyboardType.Text),
            modifier.align(Alignment.CenterHorizontally)
        ) {
            viewModel.submitIntent(InventoryIntent.DescChanged(it))
        }
        MySpacer(16)

        Field(
            state.amount.toString(),
            TextTypeInfo(context.getString(com.dralsoft.inventory.R.string.amount), KeyboardType.Number),
            modifier.align(Alignment.CenterHorizontally)
        ) {
            viewModel.submitIntent(InventoryIntent.AmountChanged(it))
        }

        MySpacer(16)

        Button(enabled = state.saveEnabled, modifier = modifier.height(55.dp), onClick = {
            viewModel.submitIntent(InventoryIntent.Save)
        }) {
            Text(text = context.getString(com.dralsoft.inventory.R.string.save))
        }
    }
}


@Composable
fun Field(text: String, info: TextTypeInfo, modifier: Modifier, onTextChange: (String) -> Unit) {

    OutlinedTextField(
        value = text, onValueChange = { onTextChange(it) },
        modifier = modifier,
        placeholder = {
            Text(text = info.text)
        },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = info.type)
    )
}

data class TextTypeInfo(val text: String, val type: KeyboardType)
