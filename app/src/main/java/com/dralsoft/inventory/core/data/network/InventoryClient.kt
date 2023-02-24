package com.dralsoft.inventory.core.data.network

import com.dralsoft.inventory.detail.data.response.InventoryResponse
import com.dralsoft.inventory.list.data.response.ListInventoryResponse
import retrofit2.Response
import retrofit2.http.GET

interface InventoryClient {
    @GET("/")
    suspend fun listInventory(): Response<ListInventoryResponse>

    @GET("/")
    suspend fun getInventory(): Response<InventoryResponse>
}