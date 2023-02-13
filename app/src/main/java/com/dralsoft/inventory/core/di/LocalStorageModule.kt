package com.dralsoft.inventory.core.di

import com.dralsoft.inventory.detail.data.local.InventoryLocalStorage
import com.dralsoft.inventory.list.data.local.ListInventoryLocalStorage
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
    fun provideMockListData(): ListInventoryLocalStorage {
        return ListInventoryLocalStorage()
    }

    @Provides
    @Singleton
    fun provideMockInventoryData(): InventoryLocalStorage {
        return InventoryLocalStorage()
    }
}