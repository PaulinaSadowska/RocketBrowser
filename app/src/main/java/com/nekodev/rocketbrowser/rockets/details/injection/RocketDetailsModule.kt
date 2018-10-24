package com.nekodev.rocketbrowser.rockets.details.injection

import com.nekodev.rocketbrowser.rockets.details.RocketDetailsContract
import com.nekodev.rocketbrowser.rockets.details.RocketDetailsPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class RocketDetailsModule {
    @Binds
    abstract fun getPresenter(presenter: RocketDetailsPresenter): RocketDetailsContract.Presenter
}