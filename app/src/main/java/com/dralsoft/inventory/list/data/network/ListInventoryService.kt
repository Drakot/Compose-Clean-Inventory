package com.dralsoft.inventory.list.data.network

import com.dralsoft.inventory.list.data.response.ListInventoryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ListInventoryService @Inject constructor(private val client: ListInventoryClient) {

    suspend fun listInventory(): ListInventoryResponse? {
        return withContext(Dispatchers.IO) {
            val response = client.listInventory()
            response.body()
        }

    }
}