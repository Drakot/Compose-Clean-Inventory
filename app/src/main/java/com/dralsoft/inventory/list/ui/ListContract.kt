package com.dralsoft.inventory.list.ui

import com.dralsoft.inventory.core.ui.SearchWidgetState
import com.dralsoft.inventory.core.ui.mvi.MviIntent
import com.dralsoft.inventory.core.ui.mvi.MviSingleEvent
import com.dralsoft.inventory.core.ui.mvi.MviViewState
import com.dralsoft.inventory.list.data.response.InventoryItem

sealed class ListIntent : MviIntent {
    data class Load(val text: String="") : ListIntent()
    data class InventoryClick(val id: Long) : ListIntent()
    object AddInventory : ListIntent()
    object OnSearchClicked : ListIntent()
    object OnCloseSearchClick : ListIntent()
    data class OnSearch(val text: String) : ListIntent()
    data class OnTypeSearch(val text: String) : ListIntent()
}

data class ListInventoryState(
    val data: List<InventoryItem> = listOf(),
    val isLoading: Boolean = false,
    val searchState: SearchWidgetState = SearchWidgetState.CLOSED,
    val searchText: String = ""
) : MviViewState

sealed class ListUiSingleEvent : MviSingleEvent {
    data class Error(val errorMessage: String) : ListUiSingleEvent()
    data class OpenDetailScreen(val navRoute: String) : ListUiSingleEvent()
}