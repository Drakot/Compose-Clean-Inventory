package com.dralsoft.inventory.detail.domain

import com.dralsoft.inventory.core.Resource
import com.dralsoft.inventory.core.domain.Repository
import com.dralsoft.inventory.detail.data.response.InventoryResponse
import com.dralsoft.inventory.list.data.response.InventoryItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveInventoryUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(item: InventoryItem): Flow<Resource<InventoryResponse>> {
        if (item.id == 0L) return repository.createInventory(item)
        return repository.saveInventory(item)
    }
}