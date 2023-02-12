package com.dralsoft.inventory.list.data.local

import com.dralsoft.inventory.list.data.response.*
import kotlinx.coroutines.delay
import retrofit2.Response

class LocalStorage {
    suspend fun listInventory(): Response<ListInventoryResponse> {
        delay(2000)
        return Response.success(
            ListInventoryResponse(
                listOf(
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
                        ),
                        1
                    ),
                    InventoryItem(
                        InventoryAttributes(
                            3,
                            "description",
                            "Cajonera dentro de Armario",
                            "Relay 3V",
                            1,
                            listOf(
                                "https://www.embtronik.com/wp-content/uploads/2020/01/1-Channel-5V-Relay-Module-Shield-for-1.jpg"
                            ),
                            "OK",
                            "2021-01-01T00:00:00.000Z"
                        ), 1
                    ),
                ),
                Meta(Pagination(1, 1, 20, 50))
            )
        )
    }
}