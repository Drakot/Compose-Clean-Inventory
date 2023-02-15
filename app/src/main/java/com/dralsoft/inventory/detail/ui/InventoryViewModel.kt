package com.dralsoft.inventory.detail.ui

import androidx.lifecycle.viewModelScope
import com.dralsoft.inventory.core.ui.AbstractMviViewModel
import com.dralsoft.inventory.detail.data.response.InventoryResponse
import com.dralsoft.inventory.detail.domain.InventoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val useCase: InventoryUseCase
) : AbstractMviViewModel<InventoryIntent, InventoryState, InventorySingleEvent<InventoryResponse>>() {

    override fun initState() = InventoryState()

    override fun submitIntent(intent: InventoryIntent) {
        when (intent) {
            is InventoryIntent.Load -> {
                load(intent.inventoryId)
            }
            is InventoryIntent.Save -> {
                //TODO pending save call, usecase, etc
                onSave()
            }
            is InventoryIntent.AmountChanged -> {
                if(intent.amount.isEmpty()) {
                    submitState(viewState.value.copy(amount = 0))
                    return
                }
            }
            is InventoryIntent.DescChanged -> {
                submitState(viewState.value.copy(description = intent.desc))
            }
            is InventoryIntent.NameChanged -> {
                submitState(viewState.value.copy(name = intent.name))
            }
        }
    }

    private fun onSave() {
        viewState.value.let {

            // submitSingleEvent(InventorySingleEvent.OnSaveSucess(InventoryResponse(it.name, it.description, it.amount)))
        }
    }

    private fun load(inventoryId: Long?) {
        viewModelScope.launch {
            inventoryId?.let {
                val response = useCase.invoke(inventoryId)

                if (response.isSuccessful) {
                    response.body()?.let {
                        submitSingleEvent(InventorySingleEvent.OnLoadSuccess(it))
                    }
                } else {
                    submitSingleEvent(InventorySingleEvent.Error(response.message()))
                }
            }
        }
    }

}
