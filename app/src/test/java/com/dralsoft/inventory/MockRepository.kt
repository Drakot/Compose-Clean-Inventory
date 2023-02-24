package com.dralsoft.inventory

import com.dralsoft.inventory.core.domain.Repository
import com.dralsoft.inventory.detail.data.response.InventoryResponse
import com.dralsoft.inventory.list.data.local.InventoryLocalStorage
import com.dralsoft.inventory.list.data.response.InventoryItem
import com.dralsoft.inventory.list.data.response.ListInventoryResponse
import retrofit2.Response

class MockRepository : Repository {
    override suspend fun listInventory(text: String): Response<ListInventoryResponse> {
        return InventoryLocalStorage().listInventory("")
    }

    override suspend fun delete(id: Long): Response<InventoryResponse> {
        return InventoryLocalStorage().getInventory(id)
    }

    override suspend fun getInventory(id: Long): Response<InventoryResponse> {
        return InventoryLocalStorage().getInventory(id)
    }

    override suspend fun saveInventory(item: InventoryItem): Response<InventoryResponse> {
        return Response.success(InventoryResponse(item))
    }
}