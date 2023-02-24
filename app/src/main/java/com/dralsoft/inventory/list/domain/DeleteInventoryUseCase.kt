package com.dralsoft.inventory.list.domain

import com.dralsoft.inventory.core.Resource
import com.dralsoft.inventory.core.domain.Repository
import com.dralsoft.inventory.detail.data.response.InventoryResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

 class DeleteInventoryUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(id: Long): Flow<Resource<InventoryResponse>> {
        return repository.delete(id)
    }
}