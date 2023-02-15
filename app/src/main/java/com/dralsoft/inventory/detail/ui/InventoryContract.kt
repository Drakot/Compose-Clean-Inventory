package com.dralsoft.inventory.detail.ui

import com.dralsoft.inventory.core.ui.MviIntent
import com.dralsoft.inventory.core.ui.MviSingleEvent
import com.dralsoft.inventory.core.ui.MviViewState
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
    val description: String = "",
    val amount: Int = 0
) : MviViewState{
    //function named map that creates an instance of InventoryItem
    fun map() = InventoryItem(id, InventoryAttributes(name, description, amount))
}

sealed class InventorySingleEvent<out T : Any> : MviSingleEvent {
    data class Error(val errorMessage: String) : InventorySingleEvent<Nothing>()
    data class OnLoadSuccess<T : Any>(val data: T) : InventorySingleEvent<T>()
    data class OnSaveSuccess<T : Any>(val data: T) : InventorySingleEvent<T>()
}

