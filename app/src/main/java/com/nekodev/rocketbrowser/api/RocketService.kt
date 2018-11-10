package com.nekodev.rocketbrowser.api

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RocketService {

    @GET("rockets")
    fun getRockets(): Deferred<List<Rocket>>

    @GET("rockets/{rocketId}")
    fun getRocketDetails(@Path("rocketId") rocketId: String): Deferred<RocketDetails>

    @GET("launches")
    fun getRocketLaunches(@Query("rocket_id") rocketId: String): Deferred<List<RocketLaunch>>
}