package com.dralsoft.inventory.list.ui

import androidx.lifecycle.viewModelScope
import com.dralsoft.inventory.core.navigation.InventoryItemInput
import com.dralsoft.inventory.core.navigation.NavRoutes
import com.dralsoft.inventory.core.ui.SearchWidgetState
import com.dralsoft.inventory.core.ui.mvi.AbstractMviViewModel
import com.dralsoft.inventory.list.domain.ListUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListInventoryViewModel @Inject constructor(
    private val useCases: ListUseCases
) : AbstractMviViewModel<ListIntent, ListInventoryState, ListUiSingleEvent>() {


    private var listJob: Job? = null

    init {
        submitIntent(ListIntent.Load())
    }

    override fun submitIntent(intent: ListIntent) {
        when (intent) {
            is ListIntent.Load -> {
                submitState(viewState.value.copy(isLoading = true))
                load(intent.text)
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
            ListIntent.AddInventory -> {
                submitSingleEvent(
                    ListUiSingleEvent.OpenDetailScreen(
                        NavRoutes.NewInventory.route
                    )
                )
            }

            is ListIntent.OnCloseSearchClick -> {
                submitState(viewState.value.copy(searchState = SearchWidgetState.CLOSED))
                load()
            }
            is ListIntent.OnSearch -> {
                //Call service to search
                load(intent.text)
            }
            is ListIntent.OnSearchClicked -> {
                submitState(viewState.value.copy(searchState = SearchWidgetState.OPENED))
            }
            is ListIntent.OnTypeSearch -> {
                submitState(viewState.value.copy(searchText = intent.text))
                //Call service to search if needed
            }
        }
    }

    override fun initState(): ListInventoryState = ListInventoryState(isLoading = true)


    private fun load(text: String = "") {
        listJob?.cancel()
        listJob = viewModelScope.launch {
            val response = useCases.listInventoryUseCase.invoke(text)
            submitState(viewState.value.copy(isLoading = false))
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
