package com.dralsoft.inventory.detail.data.local

import com.dralsoft.inventory.detail.data.response.InventoryResponse
import com.dralsoft.inventory.list.data.local.mockInventoryResponse
import com.dralsoft.inventory.list.data.response.InventoryItem
import kotlinx.coroutines.delay
import retrofit2.Response

class InventoryLocalStorage {
    suspend fun getInventory(id: Long): Response<InventoryResponse> {
        delay(500)
        return Response.success(
            InventoryResponse(
                mockInventoryResponse().data.find { it.id == id }!!
            )
        )
    }

    suspend fun saveInventory(item: InventoryItem): Response<InventoryResponse> {
        delay(500)
        return Response.success(
            InventoryResponse(
                item
            )
        )
    }
}