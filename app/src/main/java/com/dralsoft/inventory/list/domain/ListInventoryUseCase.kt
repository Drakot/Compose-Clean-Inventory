package com.dralsoft.inventory.list.domain

import com.dralsoft.inventory.core.Resource
import com.dralsoft.inventory.core.domain.Repository
import com.dralsoft.inventory.list.data.response.ListInventoryResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ListInventoryUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(text: String=""): Flow<Resource<ListInventoryResponse>> = flow {
        val list = repository.listInventory(text)
        emit(list.first())
    }
}