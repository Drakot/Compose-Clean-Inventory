package com.dralsoft.inventory.core.di

import com.dralsoft.inventory.core.data.local.InventoryLocalStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalStorageModule {

    @Provides
    @Singleton
    fun provideInventoryLocalStorage(): InventoryLocalStorage {
        return InventoryLocalStorage()
    }
}