package com.learncompose.networks

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun baseUrl(): String = "https://www.7timer.info/bin/?lon=113.2&lat=23.1&ac=0&unit=metric&output=json&tzshift=0"

    @Singleton
    @Provides
    fun okHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS).addInterceptor(loggingInterceptor).build()
    }

    @Singleton
    @Provides
    fun gsonConverter(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun retrofit(
        baseUrl: String, okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient).addConverterFactory(
            gsonConverterFactory
        ).build()
    }

    @Singleton
    @Provides
    fun retrofitApi(
        retrofit: Retrofit
    ):ApiService{
        return  retrofit.create(ApiService::class.java)
    }


    @Singleton
    @Provides
    fun getFirebaseInstance(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

}