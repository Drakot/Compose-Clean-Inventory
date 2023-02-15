package com.dralsoft.inventory.list.ui

import androidx.lifecycle.viewModelScope
import com.dralsoft.inventory.core.navigation.InventoryItemInput
import com.dralsoft.inventory.core.navigation.NavRoutes
import com.dralsoft.inventory.core.ui.AbstractMviViewModel
import com.dralsoft.inventory.list.domain.ListInventoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListInventoryViewModel @Inject constructor(
    private val useCase: ListInventoryUseCase
) : AbstractMviViewModel<ListIntent, ListInventoryState, ListUiSingleEvent>() {

    init {
        submitIntent(ListIntent.Load)
    }

    override fun submitIntent(intent: ListIntent) {
        when (intent) {
            is ListIntent.Load -> {
                load()
            }
            is ListIntent.InventoryClick -> {
                submitSingleEvent(
                    ListUiSingleEvent.OpenDetailScreen(
                        NavRoutes.Inventory.routeForInventory(
                            InventoryItemInput(intent.id)
                        )
                    )
                )
            }

        }
    }

    override fun initState(): ListInventoryState = ListInventoryState()


    private fun load() {
        viewModelScope.launch {
            val response = useCase.invoke()

            if (response.isSuccessful) {
                response.body()?.let {
                    submitState(viewState.value.copy(data = it.data))
                }
            } else {
                submitSingleEvent(ListUiSingleEvent.Error(response.message()))
            }
        }
    }

}
