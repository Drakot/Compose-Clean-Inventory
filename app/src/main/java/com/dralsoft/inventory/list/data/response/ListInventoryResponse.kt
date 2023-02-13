package com.dralsoft.inventory.list.data.response

data class ListInventoryResponse(
    val `data`: List<InventoryItem>,
    val meta: Meta
)

data class InventoryItem(
    val attributes: InventoryAttributes,
    val id: Long
)

data class InventoryAttributes(
    val amount: Int,
    val description: String,
    val location: String,
    val name: String,
    val order: Int,
    val pictures: List<String>,
    val status: String,
    val updatedAt: String
)