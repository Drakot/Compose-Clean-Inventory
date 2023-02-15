package com.dralsoft.inventory.list.ui

import com.dralsoft.inventory.core.ui.MviIntent
import com.dralsoft.inventory.core.ui.MviSingleEvent
import com.dralsoft.inventory.core.ui.MviViewState
import com.dralsoft.inventory.list.data.response.InventoryItem

sealed class ListIntent : MviIntent {
    object Load : ListIntent()
    data class InventoryClick(val id: Long) : ListIntent()
}

data class ListInventoryState(
    val data: List<InventoryItem> = listOf(),
    val isLoading: Boolean = false
) : MviViewState

sealed class ListUiSingleEvent : MviSingleEvent {
    data class Error(val errorMessage: String) : ListUiSingleEvent()
    data class OpenDetailScreen(val navRoute: String) : ListUiSingleEvent()
}