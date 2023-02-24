package com.dralsoft.inventory.core.data

import com.dralsoft.inventory.core.Resource
import com.dralsoft.inventory.core.data.network.InventoryService
import com.dralsoft.inventory.core.domain.Repository
import com.dralsoft.inventory.detail.data.response.InventoryResponse
import com.dralsoft.inventory.list.data.local.InventoryLocalStorage
import com.dralsoft.inventory.list.data.response.InventoryItem
import com.dralsoft.inventory.list.data.response.ListInventoryResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: InventoryService,
    private val db: InventoryLocalStorage
) : Repository {

    override suspend fun listInventory(text: String): Flow<Resource<ListInventoryResponse>> {
        return flow {
            emit(Resource.Success(db.listInventory(text)))
            emit(api.listInventory())
        }
    }

    override suspend fun delete(id: Long): Flow<Resource<InventoryResponse>> {
        return flow { Resource.Success(db.delete(id)) }
    }

    override suspend fun getInventory(id: Long): Flow<Resource<InventoryResponse>> {
        return flow {
            emit(Resource.Success(db.getInventory(id)))
            emit(api.getInventory(id))
        }
    }

    override suspend fun saveInventory(item: InventoryItem): Flow<Resource<InventoryResponse>> {
        return flow {
            emit(Resource.Success(db.saveInventory(item)))
            //emit(api.saveInventory(item))
        }
    }
}