package com.nekodev.rocketbrowser.rockets.details.injection

import com.nekodev.rocketbrowser.rockets.details.RocketDetailsActivity
import dagger.Subcomponent

@Subcomponent(modules = [RocketDetailsModule::class])
interface RocketDetailsComponent {
    fun inject(activity: RocketDetailsActivity)
}