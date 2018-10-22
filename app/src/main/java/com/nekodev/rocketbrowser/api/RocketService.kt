package com.nekodev.rocketbrowser.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface RocketService {

    @GET("rockets")
    fun getRockets(): Single<List<Rocket>>

    @GET("rockets/{rocketId}")
    fun getRocketDetails(@Path("rocketId") rocketId: String): Single<List<Rocket>>
}

data class Rocket(val id: String) //todo