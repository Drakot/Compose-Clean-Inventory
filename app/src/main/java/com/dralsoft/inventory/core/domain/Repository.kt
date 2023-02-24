package com.dralsoft.inventory.core.domain

import com.dralsoft.inventory.detail.data.response.InventoryResponse
import com.dralsoft.inventory.list.data.response.InventoryItem
import com.dralsoft.inventory.list.data.response.ListInventoryResponse
import retrofit2.Response

interface Repository {
    suspend fun listInventory(text: String): Response<ListInventoryResponse>
    suspend fun delete(id: Long): Response<InventoryResponse>
    suspend fun getInventory(id: Long): Response<InventoryResponse>
    suspend fun saveInventory(item: InventoryItem): Response<InventoryResponse>
}