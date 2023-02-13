package com.dralsoft.inventory.detail.data

import com.dralsoft.inventory.detail.data.local.InventoryLocalStorage
import com.dralsoft.inventory.detail.data.network.InventoryClient
import com.dralsoft.inventory.detail.data.response.InventoryResponse
import retrofit2.Response
import javax.inject.Inject

class InventoryRepository @Inject constructor(
    private val api: InventoryClient,
    private val mock: InventoryLocalStorage
) {

    suspend fun getInventory(id:Long): Response<InventoryResponse> {
        return mock.getInventory(id)
    }
}