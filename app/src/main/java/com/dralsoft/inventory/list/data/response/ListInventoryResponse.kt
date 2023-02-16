package com.dralsoft.inventory.list.data.response

data class ListInventoryResponse(
    val `data`: List<InventoryItem>,
    val meta: Meta
)

data class InventoryItem(
    val id: Long = 0,
    val attributes: InventoryAttributes
)

data class InventoryAttributes(
    val name: String,
    val description: String,
    val amount: Int,
    val location: String = "",
    val order: Int = 0,
    val pictures: List<String> = arrayListOf(),
    val status: String = "",
    val updatedAt: String = ""
)