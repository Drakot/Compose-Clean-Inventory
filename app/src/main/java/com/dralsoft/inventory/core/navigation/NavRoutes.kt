package com.dralsoft.inventory.core.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument

private const val ROUTE_INVENTORIES = "inventory"
private const val ROUTE_INVENTORY = "inventory/%s"
private const val ROUTE_USER = "users/%s"
private const val ARG_INVENTORY_ID = "inventoryId"
private const val ARG_USER_ID = "userId"

sealed class NavRoutes(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {

    object Inventories : NavRoutes(ROUTE_INVENTORIES)

    object Inventory : NavRoutes(
        route = String.format(ROUTE_INVENTORY, "{$ARG_INVENTORY_ID}"),
        arguments = listOf(navArgument(ARG_INVENTORY_ID) {
            type = NavType.LongType
        })
    ) {

        fun routeForInventory(inventoryInput: InventoryItemInput) = String.format(ROUTE_INVENTORY, inventoryInput.id)

        fun fromEntry(entry: NavBackStackEntry): InventoryItemInput {
            return InventoryItemInput(entry.arguments?.getLong(ARG_INVENTORY_ID) ?: 0L)
        }
    }
}