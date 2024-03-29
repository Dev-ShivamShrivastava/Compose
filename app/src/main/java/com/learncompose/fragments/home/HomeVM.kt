package com.learncompose.fragments.home

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.learncompose.models.HomeDataModel
import com.learncompose.models.WeatherForecastModel
import com.learncompose.networks.ApiProcess
import com.learncompose.networks.ApiService
import com.learncompose.networks.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(val repository: Repository) : ViewModel() {

    var isShowLoader = mutableStateOf(false)
    var weatherData = mutableStateOf(WeatherForecastModel())
    init {
        callApi()
    }
    fun callApi() {
            repository.callApi(
                request = object : ApiProcess<WeatherForecastModel>{
                    override fun showLoader(loader: Boolean) {
                        isShowLoader.value =true
                    }
                    override suspend fun sendRequest(apiService: ApiService): Response<WeatherForecastModel> {
                        return apiService.getUser()
                    }

                    override fun success(response: Response<WeatherForecastModel>) {
                        isShowLoader.value =false
                        weatherData.value = response.body()?:WeatherForecastModel()
                    }

                }
            )



    }
}