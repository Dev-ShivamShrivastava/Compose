package com.learncompose.fragments.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.learncompose.models.NewsModel
import com.learncompose.networks.ApiProcess
import com.learncompose.networks.ApiService
import com.learncompose.networks.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchVM @Inject constructor(val repository: Repository) : ViewModel() {

    var isShowLoader = mutableStateOf(true)
    var newsResponse = mutableStateOf(NewsModel())

    init {
        getNews()
    }

    private fun getNews(){
        repository.callApi(
            request = object :ApiProcess<NewsModel>{
                override fun showLoader(loader: Boolean) {
                    isShowLoader.value = loader
                }

                override suspend fun sendRequest(apiService: ApiService): Response<NewsModel> {
                    return apiService.getNews()
                }

                override fun success(response: Response<NewsModel>) {
                    newsResponse.value = response.body()!!
                }

            }
        )
    }



}