package com.alexdev.myfakestoreale.di

import android.content.Context
import com.alexdev.myfakestoreale.data.local.StoreManager
import com.alexdev.myfakestoreale.data.remote.ApiService
import com.alexdev.myfakestoreale.data.repository.AuthRepositoryImpl
import com.alexdev.myfakestoreale.data.repository.ProductRepositoryImpl
import com.alexdev.myfakestoreale.domain.repository.AuthRepository
import com.alexdev.myfakestoreale.domain.repository.ProductRepository
import dagger.Binds
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
    //Login
    @Provides
    @Singleton
    fun provideAuthRepository(api: ApiService): AuthRepository {
        return AuthRepositoryImpl(api)
    }

    // instancia de ProductRepositoryImpl"
    @Provides
    @Singleton
    fun provideProductRepository(api: ApiService): ProductRepository {
        return ProductRepositoryImpl(api)
    }
}