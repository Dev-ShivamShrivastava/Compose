package com.learncompose.networks

import com.learncompose.models.WeatherForecastModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("astro.php")
    suspend fun getUser(
        @Query("lon") lon:String ="113.2",
        @Query("lat") lat:String="23.1",
        @Query("ac") ac:String ="0",
        @Query("unit") unit:String ="metric",
        @Query("output") output:String = "json",
        @Query("tzshift") tzshift:String="0",
    ):Response<WeatherForecastModel>
}