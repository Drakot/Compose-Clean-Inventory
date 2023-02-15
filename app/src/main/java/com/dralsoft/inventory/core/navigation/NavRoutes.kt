package com.dralsoft.inventory.core.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument

private const val ROUTE_INVENTORIES = "inventories"
private const val ROUTE_NEW_INVENTORY = "inventory"
private const val ROUTE_INVENTORY = "inventory/%s"
private const val ARG_INVENTORY_ID = "inventoryId"

sealed class NavRoutes(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {

    object Inventories : NavRoutes(ROUTE_INVENTORIES)
    object NewInventory : NavRoutes(ROUTE_NEW_INVENTORY)

    object Inventory : NavRoutes(
        route = String.format(ROUTE_INVENTORY, "{$ARG_INVENTORY_ID}"),
        arguments = listOf(navArgument(ARG_INVENTORY_ID) {
            type = NavType.LongType
        })
    ) {

        fun routeForInventory(inventoryInput: InventoryItemInput? = null) =
            String.format(ROUTE_INVENTORY, inventoryInput?.id)

        fun fromEntry(entry: NavBackStackEntry): InventoryItemInput {
            return InventoryItemInput(entry.arguments?.getLong(ARG_INVENTORY_ID) ?: 0L)
        }
    }
}