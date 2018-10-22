package com.nekodev.rocketbrowser.inject

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.nekodev.rocketbrowser.api.RocketService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        private const val BASE_URL = "https://api.spacexdata.com/v3/"
    }

    @Provides
    @Singleton
    fun provideRocketsService(retrofit: Retrofit): RocketService {
        return retrofit.create<RocketService>(RocketService::class.java)
    }

    @Provides
    fun provideRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }
}