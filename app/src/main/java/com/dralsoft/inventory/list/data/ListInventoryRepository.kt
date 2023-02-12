package com.dralsoft.inventory.list.data

import com.dralsoft.inventory.list.data.network.response.ListInventoryResponse
import retrofit2.Response
import javax.inject.Inject

class ListInventoryRepository @Inject constructor(private val api: ListInventoryClient) {

    suspend fun listInventory(): Response<ListInventoryResponse> {
        return api.listInventory()
    }
}