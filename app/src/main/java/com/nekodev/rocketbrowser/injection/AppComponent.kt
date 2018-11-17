package com.nekodev.rocketbrowser.injection

import android.app.Application
import com.nekodev.rocketbrowser.RocketApplication
import com.nekodev.rocketbrowser.injection.modules.DatabaseModule
import com.nekodev.rocketbrowser.injection.modules.NetworkModule
import com.nekodev.rocketbrowser.injection.modules.SchedulerModule
import com.nekodev.rocketbrowser.rockets.details.injection.RocketDetailsComponent
import com.nekodev.rocketbrowser.rockets.list.injection.RocketsComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, SchedulerModule::class, DatabaseModule::class])
interface AppComponent {
    fun inject(app: RocketApplication)
    fun getRocketsComponent(): RocketsComponent
    fun getRocketDetailsComponentBuilder(): RocketDetailsComponent.Builder

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}