package com.learncompose.networks

import android.util.Log
import com.learncompose.models.HomeDataModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService) {

    fun <T> callApi(
        request: ApiProcess<T>
    ) {
        val hitApi  = flow<Response<Any>> {
            request.showLoader(true)
            emit(request.sendRequest(apiService) as Response<Any>)
        }.flowOn(Dispatchers.IO)

        CoroutineScope(Dispatchers.Main).launch {
            hitApi.catch {it ->
                Log.d("data-->","${it.cause}")
                Log.d("data-->","${it.message}")
                Log.d("data-->","${it.localizedMessage}")
                request.showLoader(false)
            }.collect{response->
                request.showLoader(false)
                request.success(response as Response<T>)
            }
        }

    }
}