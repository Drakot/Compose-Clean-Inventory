package com.dralsoft.inventory.core.domain

import com.dralsoft.inventory.core.Resource
import com.dralsoft.inventory.detail.data.response.InventoryResponse
import com.dralsoft.inventory.list.data.response.InventoryItem
import com.dralsoft.inventory.list.data.response.ListInventoryResponse
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun listInventory(text: String): Flow<Resource<ListInventoryResponse>>
    suspend fun delete(id: Long): Flow<Resource<InventoryResponse>>
    suspend fun getInventory(id: Long): Flow<Resource<InventoryResponse>>
    suspend fun saveInventory(item: InventoryItem): Flow<Resource<InventoryResponse>>
}