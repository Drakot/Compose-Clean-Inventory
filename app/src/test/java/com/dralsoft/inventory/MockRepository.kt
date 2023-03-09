package com.dralsoft.inventory

import com.dralsoft.inventory.core.Resource
import com.dralsoft.inventory.core.data.ErrorResponse
import com.dralsoft.inventory.core.data.local.InventoryLocalStorage
import com.dralsoft.inventory.core.domain.Repository
import com.dralsoft.inventory.detail.data.response.InventoryResponse
import com.dralsoft.inventory.list.data.response.InventoryItem
import com.dralsoft.inventory.list.data.response.ListInventoryResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockRepository(val isError: Boolean = false) : Repository {

    override suspend fun listInventory(text: String): Flow<Resource<ListInventoryResponse>> = flow {

        emit(
            if (isError) Resource.Error(ErrorResponse(500, "Error"))
            else {
                Resource.Success(InventoryLocalStorage().listInventory(""))
            }
        )
    }

    override suspend fun delete(id: Long): Flow<Resource<InventoryResponse>> = flow {
        InventoryLocalStorage().delete(id)
    }

    override suspend fun getInventory(id: Long): Flow<Resource<InventoryResponse>> = flow {
        InventoryLocalStorage().getInventory(id)
    }

    override suspend fun saveInventory(item: InventoryItem): Flow<Resource<InventoryResponse>> = flow {
        InventoryResponse(item)
    }

    override suspend fun createInventory(item: InventoryItem): Flow<Resource<InventoryResponse>> {
        return flow {
            InventoryResponse(item)
        }
    }
}