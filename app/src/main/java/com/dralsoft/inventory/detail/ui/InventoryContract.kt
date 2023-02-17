package com.dralsoft.inventory.detail.ui

import com.dralsoft.inventory.core.ui.mvi.MviIntent
import com.dralsoft.inventory.core.ui.mvi.MviSingleEvent
import com.dralsoft.inventory.core.ui.mvi.MviViewState
import com.dralsoft.inventory.list.data.response.InventoryAttributes
import com.dralsoft.inventory.list.data.response.InventoryItem

sealed class InventoryIntent : MviIntent {
    data class Load(val inventoryId: Long?) : InventoryIntent()
    data class NameChanged(val name: String) : InventoryIntent()
    data class DescChanged(val desc: String) : InventoryIntent()
    data class AmountChanged(val amount: String) : InventoryIntent()
    object Save : InventoryIntent()
}

data class InventoryState(
    //val inventoryItem: InventoryItem? = null,
    val id: Long = 0,
    val name: String = "",
    val nameError: String? = null,
    val description: String = "",
    val amount: String = "",
    val amountError: String? = null,
    val isLoading: Boolean = false,
    val saveEnabled:Boolean= false
) : MviViewState {
    fun map() = InventoryItem(id, InventoryAttributes(name, description, amount.toIntOrNull() ?: 0))
}

sealed class InventorySingleEvent<out T : Any> : MviSingleEvent {
    data class Error(val errorMessage: String) : InventorySingleEvent<Nothing>()
    data class OnLoadSuccess<T : Any>(val data: T) : InventorySingleEvent<T>()
    data class OnSaveSuccess<T : Any>(val data: T) : InventorySingleEvent<T>()
}

