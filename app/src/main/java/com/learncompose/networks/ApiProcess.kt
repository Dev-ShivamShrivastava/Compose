package com.learncompose.networks

import retrofit2.Response
import retrofit2.Retrofit

interface ApiProcess<T> {
    suspend fun sendRequest(apiService: ApiService):Response<T>

    fun success(response:Response<T>)

    fun failure(message:String){}
}
