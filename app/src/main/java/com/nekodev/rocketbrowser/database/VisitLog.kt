package com.nekodev.rocketbrowser.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class VisitLog(@PrimaryKey val timestamp: Long)