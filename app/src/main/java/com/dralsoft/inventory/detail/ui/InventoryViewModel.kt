package com.dralsoft.inventory.detail.ui

import androidx.lifecycle.viewModelScope
import com.dralsoft.inventory.core.domain.ValidationResult
import com.dralsoft.inventory.core.ui.mvi.AbstractMviViewModel
import com.dralsoft.inventory.detail.data.response.InventoryResponse
import com.dralsoft.inventory.detail.domain.InventoryUseCase
import com.dralsoft.inventory.detail.domain.SaveInventoryUseCase
import com.dralsoft.inventory.detail.domain.ValidateAmount
import com.dralsoft.inventory.detail.domain.ValidateName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val useCase: InventoryUseCase,
    private val saveInventoryUseCase: SaveInventoryUseCase,
    private val validateName: ValidateName,
    private val validateAmount: ValidateAmount,
) : AbstractMviViewModel<InventoryIntent, InventoryState, InventorySingleEvent<InventoryResponse>>() {

    override fun initState() = InventoryState()

    override fun submitIntent(intent: InventoryIntent) {
        viewModelScope.launch {
            when (intent) {
                is InventoryIntent.Load -> {
                    load(intent.inventoryId)
                }

                is InventoryIntent.Save -> {
                    onSave()
                }

                is InventoryIntent.AmountChanged -> {
                    val amountResult = validateAmount.execute(intent.amount)

                    checkField(amountResult) {
                        if (!it.successful) {
                            submitState(viewState.value.copy(amountError = it.errorMessage))

                            if (intent.amount.isEmpty()) {
                                submitState(viewState.value.copy(amount = intent.amount))
                            }
                        } else {
                            submitState(viewState.value.copy(amount = intent.amount))
                        }
                    }

                }

                is InventoryIntent.DescChanged -> {
                    submitState(viewState.value.copy(description = intent.desc))
                }

                is InventoryIntent.NameChanged -> {

                    val result = validateAmount.execute(intent.name)

                    checkField(result) {
                        if (!it.successful) {
                            submitState(viewState.value.copy(nameError = it.errorMessage))
                        }
                    }

                    submitState(viewState.value.copy(name = intent.name))
                }
            }
        }
    }

    private fun checkField(flow: Flow<ValidationResult>, onFlow: (ValidationResult) -> Unit) {
        val isValid = formValidation(flow)
        isValid.onEach {
            onFlow(it)
            checkFields()
        }.launchIn(viewModelScope)
    }

    private fun checkFields() {
        val nameResult = validateName.execute(viewState.value.name)
        val amountResult = validateAmount.execute(viewState.value.amount)

        val isValid = formValidation(nameResult, amountResult)
        isValid.onEach {
            submitState(viewState.value.copy(saveEnabled = it.successful))
        }.launchIn(viewModelScope)
    }

    private fun onSave() {
        viewModelScope.launch {
            submitState(viewState.value.copy(isLoading = true, saveEnabled = false))

            val result = saveInventoryUseCase.invoke(viewState.value.map())
            submitState(viewState.value.copy(isLoading = false, saveEnabled = true))
            if (result.isSuccessful) {
                submitSingleEvent(InventorySingleEvent.OnSaveSuccess)
            } else {
                submitSingleEvent(InventorySingleEvent.Error(result.message()))
            }
        }
    }

    private fun load(inventoryId: Long?) {
        viewModelScope.launch {

            submitState(viewState.value.copy(saveEnabled = false, isLoading = true))

            inventoryId?.let {
                val response = useCase.invoke(inventoryId)

                if (response.isSuccessful) {
                    response.body()?.let {
                        val attributes = it.data.attributes
                        submitState(
                            viewState.value.copy(
                                it.data.id,
                                attributes.name,
                                attributes.description,
                                attributes.amount.toString(),
                                attributes.pictures
                            )
                        )
                    }
                } else {
                    submitSingleEvent(InventorySingleEvent.Error(response.message()))
                }
            }

            submitState(viewState.value.copy(saveEnabled = inventoryId != null, isLoading = false))
        }
    }

}

