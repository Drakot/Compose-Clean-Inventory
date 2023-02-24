package com.dralsoft.inventory.core.data.network

import com.dralsoft.inventory.core.Resource
import com.dralsoft.inventory.detail.data.response.InventoryResponse
import com.dralsoft.inventory.list.data.response.ListInventoryResponse
import javax.inject.Inject

class InventoryService @Inject constructor(private val client: InventoryClient) : BaseService() {

    suspend fun getInventory(id: Long): Resource<InventoryResponse> {
        return apiCall { client.getInventory(id) }
    }

    suspend fun listInventory(): Resource<ListInventoryResponse> {
        return apiCall { client.listInventory() }
    }

}