package com.dralsoft.inventory.core.di

import com.dralsoft.inventory.core.data.RepositoryImpl
import com.dralsoft.inventory.core.data.network.InventoryClient
import com.dralsoft.inventory.core.domain.Repository
import com.dralsoft.inventory.core.data.network.InventoryService
import com.dralsoft.inventory.list.data.local.InventoryLocalStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://google.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Singleton
    @Provides
    fun provideRepository(
        service: InventoryService,
        db: InventoryLocalStorage
    ) = RepositoryImpl(service, db) as Repository



    @Provides
    @Singleton
    fun provideClient(retrofit: Retrofit): InventoryClient {
        return retrofit.create(InventoryClient::class.java)
    }

}