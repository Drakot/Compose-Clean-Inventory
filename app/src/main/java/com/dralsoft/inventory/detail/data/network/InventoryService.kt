package com.dralsoft.inventory.detail.data.network

import com.dralsoft.inventory.core.data.network.InventoryClient
import com.dralsoft.inventory.detail.data.response.InventoryResponse
import com.dralsoft.inventory.list.data.response.ListInventoryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InventoryService @Inject constructor(private val client: InventoryClient) {

    suspend fun getInventory(): InventoryResponse? {
        return withContext(Dispatchers.IO) {
            val response = client.getInventory()
            response.body()
        }
    }

    suspend fun listInventory(): ListInventoryResponse? {
        return withContext(Dispatchers.IO) {
            val response = client.listInventory()
            response.body()
        }
    }
}