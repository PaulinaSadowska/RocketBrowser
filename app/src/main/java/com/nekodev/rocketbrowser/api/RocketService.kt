package com.nekodev.rocketbrowser.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import kotlinx.android.parcel.Parcelize
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RocketService {

    @GET("rockets")
    fun getRockets(): Single<List<Rocket>>

    @GET("rockets/{rocketId}")
    fun getRocketDetails(@Path("rocketId") rocketId: String): Single<RocketDetails>

    @GET("launches")
    fun getRocketLaunches(@Query("rocket_id") rocketId: String): Single<List<RocketLaunch>>
}

@Parcelize
data class Rocket(val id: Int,
                  @SerializedName("rocket_id") val rocketId: String,
                  @SerializedName("rocket_name") val name: String,
                  val country: String,
                  val engines: Engines,
                  val active: Boolean) : Parcelable

@Parcelize
data class Engines(val number: Int) : Parcelable

@Parcelize
data class RocketDetails(val id: Int,
                         val description: String) : Parcelable

@Parcelize
data class RocketLaunch(val id: Int,
                        val description: String) : Parcelable