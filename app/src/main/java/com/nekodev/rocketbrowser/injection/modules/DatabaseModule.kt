package com.nekodev.rocketbrowser.injection.modules

import android.app.Application
import android.arch.persistence.room.Room
import com.nekodev.rocketbrowser.database.VisitDao
import com.nekodev.rocketbrowser.database.VisitDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): VisitDatabase {
        return Room.databaseBuilder(application,
                VisitDatabase::class.java, "VisitDatabase.db")
                .build()
    }

    @Provides
    @Singleton
    fun provideVisitDao(database: VisitDatabase): VisitDao {
        return database.visitDao()
    }
}