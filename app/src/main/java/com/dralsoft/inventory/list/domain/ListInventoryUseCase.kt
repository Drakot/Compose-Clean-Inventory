package com.dralsoft.inventory.list.domain

import com.dralsoft.inventory.list.data.ListInventoryRepository
import com.dralsoft.inventory.list.data.network.response.ListInventoryResponse
import retrofit2.Response
import javax.inject.Inject

class ListInventoryUseCase @Inject constructor(private val repository: ListInventoryRepository) {
    suspend operator fun invoke(): Response<ListInventoryResponse> {
        return repository.listInventory()
    }
}