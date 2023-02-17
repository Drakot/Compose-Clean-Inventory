package com.dralsoft.inventory.detail.ui

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewModelScope
import com.dralsoft.inventory.core.ui.mvi.AbstractMviViewModel
import com.dralsoft.inventory.detail.data.response.InventoryResponse
import com.dralsoft.inventory.detail.domain.InventoryUseCase
import com.dralsoft.inventory.detail.domain.SaveInventoryUseCase
import com.dralsoft.inventory.detail.domain.ValidateAmount
import com.dralsoft.inventory.list.data.response.InventoryAttributes
import com.dralsoft.inventory.list.data.response.InventoryItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val useCase: InventoryUseCase,
    private val saveInventoryUseCase: SaveInventoryUseCase,
    private val validateAmount: ValidateAmount,
) : AbstractMviViewModel<InventoryIntent, InventoryState, InventorySingleEvent<InventoryResponse>>() {

    override fun initState() = InventoryState()

    override fun submitIntent(intent: InventoryIntent) {
        when (intent) {
            is InventoryIntent.Load -> {
                load(intent.inventoryId)
            }

            is InventoryIntent.Save -> {
                onSave()
            }

            is InventoryIntent.AmountChanged -> {
                if (intent.amount.isNotEmpty() && intent.amount.isDigitsOnly()) {
                    submitState(viewState.value.copy(amount = intent.amount))
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
        viewModelScope.launch {
            saveInventoryUseCase.invoke(viewState.value.map())
            viewState.value.let {
                // submitSingleEvent(InventorySingleEvent.OnSaveSucess(InventoryResponse(it.name, it.description, it.amount)))
            }
        }
    }

    private fun load(inventoryId: Long?) {
        viewModelScope.launch {
            submitState(viewState.value.copy(isLoading = true))
            inventoryId?.let {
                val response = useCase.invoke(inventoryId)
                submitState(viewState.value.copy(isLoading = false))
                if (response.isSuccessful) {
                    response.body()?.let {
                        submitState(
                            viewState.value.copy(
                                it.data.id,
                                it.data.attributes.name,
                                it.data.attributes.description,
                                it.data.attributes.amount.toString()
                            )
                        )
                    }
                } else {
                    submitSingleEvent(InventorySingleEvent.Error(response.message()))
                }
            }
        }
    }

}
