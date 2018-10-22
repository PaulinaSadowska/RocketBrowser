package com.nekodev.rocketbrowser

import android.app.Application
import com.nekodev.rocketbrowser.injection.AppComponent
import com.nekodev.rocketbrowser.injection.DaggerAppComponent

class RocketApplication : Application() {

    lateinit var component: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder().build()
    }
}