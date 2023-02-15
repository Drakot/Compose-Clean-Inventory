package com.dralsoft.inventory.list.ui

import com.dralsoft.inventory.core.ui.mvi.MviIntent
import com.dralsoft.inventory.core.ui.mvi.MviSingleEvent
import com.dralsoft.inventory.core.ui.mvi.MviViewState
import com.dralsoft.inventory.list.data.response.InventoryItem

sealed class ListIntent : MviIntent {
    object Load : ListIntent()
    data class InventoryClick(val id: Long) : ListIntent()
    object AddInventory : ListIntent()
}

data class ListInventoryState(
    val data: List<InventoryItem> = listOf(),
    val isLoading: Boolean = false
) : MviViewState

sealed class ListUiSingleEvent : MviSingleEvent {
    data class Error(val errorMessage: String) : ListUiSingleEvent()
    data class OpenDetailScreen(val navRoute: String) : ListUiSingleEvent()
}