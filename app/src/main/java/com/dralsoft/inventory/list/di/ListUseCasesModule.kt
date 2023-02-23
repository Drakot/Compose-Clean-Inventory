package com.dralsoft.inventory.list.di

import com.dralsoft.inventory.list.data.ListInventoryRepository
import com.dralsoft.inventory.list.domain.DeleteInventoryUseCase
import com.dralsoft.inventory.list.domain.ListInventoryUseCase
import com.dralsoft.inventory.list.domain.ListUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ListUseCasesModule {

    @Provides
    @Singleton
    fun provideListUseCases(repository: ListInventoryRepository): ListUseCases {
        return ListUseCases(
            deleteInventoryUseCase = DeleteInventoryUseCase(repository),
            listInventoryUseCase = ListInventoryUseCase(repository),
        )
    }
}
