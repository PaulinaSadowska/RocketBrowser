package com.nekodev.rocketbrowser.rockets.injection

import com.nekodev.rocketbrowser.rockets.RocketsContract
import com.nekodev.rocketbrowser.rockets.RocketsPresenter
import dagger.Binds
import dagger.Module

@Module
interface RocketsModule {
    @Binds
    fun getPresenter(presenter: RocketsPresenter): RocketsContract.Presenter
}