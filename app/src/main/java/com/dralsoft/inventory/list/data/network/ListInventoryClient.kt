package com.dralsoft.inventory.list.data.network

import com.dralsoft.inventory.list.data.response.ListInventoryResponse
import retrofit2.Response
import retrofit2.http.GET

interface ListInventoryClient {
    @GET("/")
    suspend fun listInventory(): Response<ListInventoryResponse>
}