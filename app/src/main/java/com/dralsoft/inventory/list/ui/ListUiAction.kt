package com.dralsoft.inventory.list.ui

import com.dralsoft.inventory.core.ui.UiAction

sealed class ListUiAction : UiAction {
    object Load : ListUiAction()
    data class InventoryClick(val id: Long) : ListUiAction()
}