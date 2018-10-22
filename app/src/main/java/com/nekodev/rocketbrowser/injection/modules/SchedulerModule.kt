package com.nekodev.rocketbrowser.injection.modules

import com.nekodev.rocketbrowser.util.BaseSchedulerProvider
import com.nekodev.rocketbrowser.util.SchedulerProvider
import dagger.Binds
import dagger.Module

@Module
interface SchedulerModule {
    @Binds
    fun provideSchedulerProvider(schedulerProvider: SchedulerProvider): BaseSchedulerProvider
}