package com.dralsoft.inventory.detail.domain

data class DetailUseCases(
    val validateAmount: ValidateAmount,
    val validateName: ValidateName,
    val saveInventoryUseCase: SaveInventoryUseCase,
    val inventoryUseCase: InventoryUseCase
)
