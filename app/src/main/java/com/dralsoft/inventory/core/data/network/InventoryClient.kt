package com.dralsoft.inventory.core.data.network

import com.dralsoft.inventory.detail.data.response.InventoryResponse
import com.dralsoft.inventory.list.data.response.ListInventoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface InventoryClient {
    @GET("/")
    suspend fun listInventory(): Response<ListInventoryResponse>

    @GET("/inventory/{id}")
    suspend fun getInventory(@Path("id") id: Long): Response<InventoryResponse>
}