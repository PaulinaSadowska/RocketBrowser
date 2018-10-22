package com.nekodev.rocketbrowser.rockets.list.injection

import com.nekodev.rocketbrowser.rockets.list.RocketsActivity
import dagger.Subcomponent

@Subcomponent(modules = [RocketsModule::class])
interface RocketsComponent{
    fun inject(activity: RocketsActivity)
}
