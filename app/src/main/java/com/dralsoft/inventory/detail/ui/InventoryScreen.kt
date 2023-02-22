package com.dralsoft.inventory.detail.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material.icons.rounded.PhotoCamera
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dralsoft.inventory.R
import com.dralsoft.inventory.core.Loading
import com.dralsoft.inventory.core.ScaffoldView
import com.dralsoft.inventory.core.ViewConfig
import com.dralsoft.inventory.core.navigation.InventoryItemInput
import com.dralsoft.inventory.core.ui.MySpacer
import com.dralsoft.inventory.core.ui.mvi.collectInLaunchedEffectWithLifecycle
import kotlinx.coroutines.CoroutineScope
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
        ScaffoldView(ViewConfig(showBackButton = true).copy(onClickNavIcon = {
            navController.popBackStack()
        })) {
            Form(viewModel, scope)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Form(viewModel: InventoryViewModel, scope: CoroutineScope) {
    val state = viewModel.viewState.collectAsState().value
    val context = LocalContext.current
    val modifier = Modifier.fillMaxWidth()

    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        viewModel.submitIntent(InventoryIntent.ImageAdded(uri))
    }

    Column(modifier = Modifier.padding(16.dp)) {
        PictureGridView(pictures = state.pictures, {
            scope.launch {
                if (it.scheme == null) {
                    if (modalSheetState.isVisible)
                        modalSheetState.hide()
                    else
                        modalSheetState.animateTo(ModalBottomSheetValue.Expanded)
                }
            }
        }, {

        })

        Field(
            state.name,
            TextTypeInfo(context.getString(com.dralsoft.inventory.R.string.name), KeyboardType.Text),
            modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp)
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
            state.amount,
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

    BottomSheetLayout(modalSheetState) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {

            BottomSheetItems("Gallery", Icons.Rounded.Image) {
                scope.launch {
                    modalSheetState.hide()
                }
                singlePhotoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

            }
            BottomSheetItems("Camera", Icons.Rounded.PhotoCamera, com.dralsoft.inventory.ui.theme.Green) {

            }
        }
    }
    Loading(state.isLoading)
}


@Composable
fun Field(text: String, info: TextTypeInfo, modifier: Modifier, onTextChange: (String) -> Unit) {

    OutlinedTextField(
        value = text, onValueChange = { onTextChange(it) },
        modifier = modifier,
        label = { Text(info.text) },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = info.type)
    )
}

data class TextTypeInfo(val text: String, val type: KeyboardType)
