package com.dralsoft.inventory.list.domain

import com.dralsoft.inventory.detail.data.response.InventoryResponse
import com.dralsoft.inventory.list.data.ListInventoryRepository
import retrofit2.Response
import javax.inject.Inject

class DeleteInventoryUseCase @Inject constructor(private val repository: ListInventoryRepository) {
    suspend operator fun invoke(id: Long): Response<InventoryResponse> {
        return repository.delete(id)
    }
}