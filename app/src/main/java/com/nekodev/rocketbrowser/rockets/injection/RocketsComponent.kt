package com.nekodev.rocketbrowser.rockets.injection

import com.nekodev.rocketbrowser.rockets.RocketsActivity
import dagger.Subcomponent

@Subcomponent(modules = [RocketsModule::class])
interface RocketsComponent{
    fun inject(activity: RocketsActivity)
}
