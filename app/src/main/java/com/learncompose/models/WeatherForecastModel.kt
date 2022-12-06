package com.learncompose.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherForecastModel(
    @SerializedName("dataseries")
    var dataseries: ArrayList<Datasery>? = null,
    @SerializedName("init")
    var `init`: String? = null,
    @SerializedName("product")
    var product: String? = null
) : Parcelable {
    @Parcelize
    data class Datasery(
        @SerializedName("cloudcover")
        var cloudcover: Int? = null,
        @SerializedName("lifted_index")
        var liftedIndex: Int? = null,
        @SerializedName("prec_type")
        var precType: String? = null,
        @SerializedName("rh2m")
        var rh2m: Int? = null,
        @SerializedName("seeing")
        var seeing: Int? = null,
        @SerializedName("temp2m")
        var temp2m: Int? = null,
        @SerializedName("timepoint")
        var timepoint: Int? = null,
        @SerializedName("transparency")
        var transparency: Int? = null,
        @SerializedName("wind10m")
        var wind10m: Wind10m? = null,
        var isSelected:Boolean=false
    ) : Parcelable {
        @Parcelize
        data class Wind10m(
            @SerializedName("direction")
            var direction: String? = null,
            @SerializedName("speed")
            var speed: Int? = null
        ) : Parcelable
    }
}