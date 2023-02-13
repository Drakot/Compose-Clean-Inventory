package com.dralsoft.inventory.detail.data.local

import com.dralsoft.inventory.detail.data.response.InventoryResponse
import com.dralsoft.inventory.list.data.response.InventoryAttributes
import com.dralsoft.inventory.list.data.response.InventoryItem
import kotlinx.coroutines.delay
import retrofit2.Response

class InventoryLocalStorage {
    suspend fun getInventory(id: Long): Response<InventoryResponse> {
        delay(300)
        return Response.success(
            InventoryResponse(
                InventoryItem(
                    InventoryAttributes(
                        6,
                        "description",
                        "Cajonera dentro de Armario",
                        "Relay 5V",
                        1,
                        listOf(
                            "https://static.cytron.io/image/cache/catalog/products/BB-RELAY-5V-01/BB-RELAY-5V-01-800x800.jpg",
                            "https://http2.mlstatic.com/D_NQ_NP_852723-MLA52961083957_122022-O.webp"
                        ),
                        "OK",
                        "2021-01-01T00:00:00.000Z"
                    ), 1
                )
            )
        )
    }
}