package com.dralsoft.inventory.detail.ui

import androidx.lifecycle.viewModelScope
import com.dralsoft.inventory.core.ui.InventoryUiState
import com.dralsoft.inventory.core.ui.MviViewModel
import com.dralsoft.inventory.core.ui.UiSingleEvent
import com.dralsoft.inventory.core.ui.UiState
import com.dralsoft.inventory.detail.data.response.InventoryResponse
import com.dralsoft.inventory.detail.domain.InventoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(private val useCase: InventoryUseCase) :
    MviViewModel<InventoryResponse, InventoryUiState<InventoryResponse>, InventoryUiAction, UiSingleEvent>() {

    override fun initState(): InventoryUiState<InventoryResponse> = InventoryUiState<InventoryResponse>
    override fun handleAction(action: InventoryUiAction) {
        when (action) {
            is InventoryUiAction.Load -> {
                load()
            }
            is InventoryUiAction.InventoryClick -> {

            }
            is InventoryUiAction.AmountChanged -> {

            }
            is InventoryUiAction.DescChanged -> {

            }
            is InventoryUiAction.NameChanged -> {

            }
        }
    }

    private fun load() {
        viewModelScope.launch {
            val response = useCase.invoke(1)

            if (response.isSuccessful) {
                response.body()?.let {
                    submitState(UiState.Success(it))
                }
            } else {
                submitState(UiState.Error(response.message()))
            }
        }
    }

}
