package com.dralsoft.inventory.detail.data.network

import com.dralsoft.inventory.detail.data.response.InventoryResponse
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
}