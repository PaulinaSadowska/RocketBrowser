package com.nekodev.rocketbrowser.rockets.details.injection

import com.nekodev.rocketbrowser.rockets.details.RocketDetailsActivity
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [RocketDetailsModule::class])
interface RocketDetailsComponent {
    fun inject(activity: RocketDetailsActivity)

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun rocketData(rocketData: RocketInitData): RocketDetailsComponent.Builder

        fun build(): RocketDetailsComponent
    }
}