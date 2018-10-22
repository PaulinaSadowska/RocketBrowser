package com.nekodev.rocketbrowser.api

import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface RocketService {

    @GET("rockets")
    fun getRockets(): Single<List<Rocket>>

    @GET("rockets/{rocketId}")
    fun getRocketDetails(@Path("rocketId") rocketId: String): Single<RocketDetails>
}

data class Rocket(val id: Int,
                  @SerializedName("rocket_id") val rocketId: String,
                  @SerializedName("rocket_name") val name: String,
                  val country: String,
                  val engines: Engines,
                  val active: Boolean)

data class Engines(val number: Int)

data class RocketDetails(val id: Int)