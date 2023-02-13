package com.dralsoft.inventory.detail.ui

import com.dralsoft.inventory.core.ui.UiAction

sealed class InventoryUiAction : UiAction {
    data class Load(val inventoryId: Long) : InventoryUiAction()
    data class InventoryClick(val id: Long) : InventoryUiAction()
}