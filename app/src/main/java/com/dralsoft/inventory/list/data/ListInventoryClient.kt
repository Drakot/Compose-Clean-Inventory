package com.dralsoft.inventory.list.data

import com.dralsoft.inventory.list.data.network.response.ListInventoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ListInventoryClient {
    @GET("/")
    suspend fun listInventory(): Response<ListInventoryResponse>
}