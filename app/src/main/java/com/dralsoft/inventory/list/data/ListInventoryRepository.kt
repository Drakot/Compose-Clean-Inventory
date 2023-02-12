package com.dralsoft.inventory.list.data

import com.dralsoft.inventory.list.data.local.LocalStorage
import com.dralsoft.inventory.list.data.network.ListInventoryClient
import com.dralsoft.inventory.list.data.response.ListInventoryResponse
import retrofit2.Response
import javax.inject.Inject

class ListInventoryRepository @Inject constructor(
    private val api: ListInventoryClient,
    private val mock: LocalStorage
) {

    suspend fun listInventory(): Response<ListInventoryResponse> {
        return mock.listInventory()
    }
}