package com.dralsoft.inventory.core.data

import com.dralsoft.inventory.core.domain.Repository
import com.dralsoft.inventory.detail.data.network.InventoryService
import com.dralsoft.inventory.detail.data.response.InventoryResponse
import com.dralsoft.inventory.list.data.local.InventoryLocalStorage
import com.dralsoft.inventory.list.data.response.InventoryItem
import com.dralsoft.inventory.list.data.response.ListInventoryResponse
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: InventoryService,
    private val db: InventoryLocalStorage
) : Repository{

    override suspend fun listInventory(text: String): Response<ListInventoryResponse> {

        return db.listInventory(text)
    }

    override suspend fun delete(id: Long): Response<InventoryResponse> {
        return db.delete(id)
    }

    override suspend fun getInventory(id:Long): Response<InventoryResponse> {
        return db.getInventory(id)
    }

    override suspend fun saveInventory(item: InventoryItem): Response<InventoryResponse> {
        return db.saveInventory(item)
    }
}