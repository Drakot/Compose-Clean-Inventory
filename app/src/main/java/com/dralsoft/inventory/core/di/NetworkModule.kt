package com.dralsoft.inventory.core.di

import com.dralsoft.inventory.list.data.local.LocalStorage
import com.dralsoft.inventory.list.data.network.ListInventoryClient
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

    @Provides
    @Singleton
    fun provideListClient(retrofit: Retrofit): ListInventoryClient {
        return retrofit.create(ListInventoryClient::class.java)
    }

    @Provides
    @Singleton
    fun provideMockListData(): LocalStorage {
        return LocalStorage()
    }
}