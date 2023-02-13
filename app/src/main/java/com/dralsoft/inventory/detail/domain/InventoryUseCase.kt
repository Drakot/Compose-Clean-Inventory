package com.dralsoft.inventory.detail.domain

import com.dralsoft.inventory.detail.data.InventoryRepository
import com.dralsoft.inventory.detail.data.response.InventoryResponse
import retrofit2.Response
import javax.inject.Inject

class InventoryUseCase @Inject constructor(private val repository: InventoryRepository) {
    suspend operator fun invoke(id:Long): Response<InventoryResponse> {
        return repository.getInventory(id)
    }
}