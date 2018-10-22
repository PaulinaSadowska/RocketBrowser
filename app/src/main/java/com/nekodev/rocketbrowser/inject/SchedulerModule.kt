package com.nekodev.rocketbrowser.inject

import com.nekodev.rocketbrowser.BaseSchedulerProvider
import com.nekodev.rocketbrowser.SchedulerProvider
import dagger.Binds
import dagger.Module

@Module
interface SchedulerModule {
    @Binds
    fun provideSchedulerProvider(schedulerProvider: SchedulerProvider): BaseSchedulerProvider
}