package com.nekodev.rocketbrowser.rockets.details.injection

import com.nekodev.rocketbrowser.rockets.details.RocketDetailsContract
import com.nekodev.rocketbrowser.rockets.details.RocketDetailsPresenter
import dagger.Binds
import dagger.Module

@Module
interface RocketDetailsModule {
    @Binds
    fun getPresenter(presenter: RocketDetailsPresenter): RocketDetailsContract.Presenter
}