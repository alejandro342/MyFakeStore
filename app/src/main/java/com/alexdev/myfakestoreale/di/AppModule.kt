package com.alexdev.myfakestoreale.di

import android.content.Context
import com.alexdev.myfakestoreale.data.local.StoreManager
import com.alexdev.myfakestoreale.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // URL Base
    @Provides
    @Singleton
    fun provideBaseUrl() = "https://fakestoreapi.com/"

    // Instancia de Retrofit
    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Instancia API vacía
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideStoreManager(@ApplicationContext context: Context): StoreManager {
        return StoreManager(context)
    }
}