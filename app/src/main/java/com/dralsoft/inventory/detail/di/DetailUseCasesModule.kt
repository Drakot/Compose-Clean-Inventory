package com.dralsoft.inventory.detail.di

import com.dralsoft.inventory.core.domain.Repository
import com.dralsoft.inventory.detail.domain.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DetailUseCasesModule {

    @Provides
    @Singleton
    fun provideDetailUseCases(repository: Repository): DetailUseCases {
        return DetailUseCases(
            validateAmount = ValidateAmount(),
            validateName = ValidateName(),
            saveInventoryUseCase = SaveInventoryUseCase(repository),
            inventoryUseCase = InventoryUseCase(repository)
        )
    }
}
