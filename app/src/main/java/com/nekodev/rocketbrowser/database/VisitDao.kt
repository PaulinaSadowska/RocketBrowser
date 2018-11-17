package com.nekodev.rocketbrowser.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import io.reactivex.Single

@Dao
interface VisitDao {

    @Insert(onConflict = REPLACE)
    fun save(visitLog: VisitLog)

    @Query("SELECT * FROM visitLog")
    fun getAllVisits(): Single<List<VisitLog>>
}