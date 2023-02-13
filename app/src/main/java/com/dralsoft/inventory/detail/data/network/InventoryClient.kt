package com.dralsoft.inventory.detail.data.network

import com.dralsoft.inventory.detail.data.response.InventoryResponse
import retrofit2.Response
import retrofit2.http.GET

interface InventoryClient {
    @GET("/")
    suspend fun getInventory(): Response<InventoryResponse>
}