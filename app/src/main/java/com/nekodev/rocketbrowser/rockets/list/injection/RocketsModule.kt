package com.nekodev.rocketbrowser.rockets.list.injection

import com.nekodev.rocketbrowser.rockets.list.RocketsContract
import com.nekodev.rocketbrowser.rockets.list.RocketsPresenter
import dagger.Binds
import dagger.Module

@Module
interface RocketsModule {
    @Binds
    fun bindPresenter(presenter: RocketsPresenter): RocketsContract.Presenter
}