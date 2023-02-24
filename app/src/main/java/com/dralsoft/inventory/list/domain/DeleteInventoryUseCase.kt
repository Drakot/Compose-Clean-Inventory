package com.dralsoft.inventory.list.domain

import com.dralsoft.inventory.core.domain.Repository
import com.dralsoft.inventory.detail.data.response.InventoryResponse
import retrofit2.Response
import javax.inject.Inject

class DeleteInventoryUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(id: Long): Response<InventoryResponse> {
        return repository.delete(id)
    }
}