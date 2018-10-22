package com.nekodev.rocketbrowser.injection

import com.nekodev.rocketbrowser.RocketApplication
import com.nekodev.rocketbrowser.injection.modules.NetworkModule
import com.nekodev.rocketbrowser.injection.modules.SchedulerModule
import com.nekodev.rocketbrowser.rockets.list.injection.RocketsComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, SchedulerModule::class])
interface AppComponent {
    fun inject(app: RocketApplication)
    fun getRocketsComponent(): RocketsComponent
}