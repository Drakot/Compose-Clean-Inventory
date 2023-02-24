package com.dralsoft.inventory.detail.domain

import com.dralsoft.inventory.core.domain.Repository
import com.dralsoft.inventory.detail.data.response.InventoryResponse
import com.dralsoft.inventory.list.data.response.InventoryItem
import retrofit2.Response
import javax.inject.Inject

class SaveInventoryUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(item:InventoryItem): Response<InventoryResponse> {
        return repository.saveInventory(item)
    }
}