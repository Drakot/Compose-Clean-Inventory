package com.dralsoft.inventory.detail.domain

import com.dralsoft.inventory.R
import com.dralsoft.inventory.detail.data.InventoryRepository
import com.dralsoft.inventory.detail.data.response.InventoryResponse
import com.dralsoft.inventory.list.data.response.InventoryItem
import retrofit2.Response
import javax.inject.Inject

class SaveInventoryUseCase @Inject constructor(private val repository: InventoryRepository) {

    suspend operator fun invoke(item:InventoryItem): Response<InventoryResponse> {
        return repository.saveInventory(item)
    }
}