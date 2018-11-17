package com.nekodev.rocketbrowser.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [VisitLog::class], version = 1)
abstract class VisitDatabase : RoomDatabase() {
    abstract fun visitDao(): VisitDao
}