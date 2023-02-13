package com.dralsoft.inventory.detail.ui

import com.dralsoft.inventory.core.ui.UiAction

sealed class InventoryUiAction : UiAction {
    object Load : InventoryUiAction()
    data class InventoryClick(val id: Long) : InventoryUiAction()
}