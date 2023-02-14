package com.dralsoft.inventory.detail.ui

import com.dralsoft.inventory.core.ui.UiState
import com.dralsoft.inventory.detail.data.response.InventoryResponse

data class InventoryUiState<T>(
    val name: String = "",
    val description: String = "",
    val amount: Int
) : UiState<InventoryResponse>()
