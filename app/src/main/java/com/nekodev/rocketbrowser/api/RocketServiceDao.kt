package com.nekodev.rocketbrowser.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Rocket(val id: Int,
                  @SerializedName("rocket_id") val rocketId: String,
                  @SerializedName("rocket_name") val name: String,
                  val country: String,
                  val engines: Engines,
                  val active: Boolean) : Parcelable

@Parcelize
data class Engines(val number: Int) : Parcelable

data class RocketDetails(val id: Int,
                         val description: String)

@Parcelize
data class RocketLaunch(@SerializedName("flight_number") val flightNumber: Int,
                        @SerializedName("mission_name") val missionName: String,
                        @SerializedName("launch_year") val launchYear: String,
                        @SerializedName("launch_date_unix") val launchTimestamp: Long,
                        val links: Links,
                        @SerializedName("launch_success") val launchSuccess: Boolean) : Parcelable

@Parcelize
data class Links(@SerializedName("mission_patch_small") val missionPatch: String) : Parcelable