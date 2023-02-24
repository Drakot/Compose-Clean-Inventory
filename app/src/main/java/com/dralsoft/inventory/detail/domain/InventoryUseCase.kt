package com.dralsoft.inventory.detail.domain

import com.dralsoft.inventory.core.Resource
import com.dralsoft.inventory.core.domain.Repository
import com.dralsoft.inventory.detail.data.response.InventoryResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InventoryUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(id:Long): Flow<Resource<InventoryResponse>> = flow{
         repository.getInventory(id).collect {
             emit(it)
         }
    }
}