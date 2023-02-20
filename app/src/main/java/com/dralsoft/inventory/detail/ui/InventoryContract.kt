package com.dralsoft.inventory.detail.ui

import android.net.Uri
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
    data class ImageAdded(val uri: Uri?) : InventoryIntent()

    object Save : InventoryIntent()
}

data class InventoryState(
    //val inventoryItem: InventoryItem? = null,
    val id: Long = 0,
    val name: String = "",
    val description: String = "",
    val amount: String = "",
    val pictures: List<Uri> = listOf(),
    val amountError: String? = null,
    val nameError: String? = null,
    var isLoading: Boolean = false,
    var saveEnabled: Boolean = false,
) : MviViewState {
    fun map() = InventoryItem(id, InventoryAttributes(name, description, amount.toIntOrNull() ?: 0))
}

data class LoadingState(
    var isLoading: Boolean = false
)


sealed class InventorySingleEvent<out T : Any> : MviSingleEvent {
    data class Error(val errorMessage: String) : InventorySingleEvent<Nothing>()
    data class OnLoadSuccess<T : Any>(val data: T) : InventorySingleEvent<T>()
    object OnSaveSuccess : InventorySingleEvent<Nothing>()
}

