package com.dralsoft.inventory.list.ui

import androidx.lifecycle.viewModelScope
import com.dralsoft.inventory.core.MviViewModel
import com.dralsoft.inventory.core.UiSingleEvent
import com.dralsoft.inventory.core.UiState
import com.dralsoft.inventory.list.data.response.ListInventoryResponse
import com.dralsoft.inventory.list.domain.ListInventoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListInventoryViewModel @Inject constructor(private val useCase: ListInventoryUseCase) :
    MviViewModel<ListInventoryResponse, UiState<ListInventoryResponse>, ListUiAction, UiSingleEvent>() {

    override fun initState(): UiState<ListInventoryResponse> = UiState.Loading
    override fun handleAction(action: ListUiAction) {
        when (action) {
            is ListUiAction.Load -> {
                load()
            }
            is ListUiAction.PostClick -> {

            }
            is ListUiAction.UserClick -> {

            }
        }
    }

    private fun load() {
        viewModelScope.launch {
            val response = useCase.invoke()

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
