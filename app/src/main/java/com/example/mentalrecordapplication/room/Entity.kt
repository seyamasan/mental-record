package com.example.mentalrecordapplication.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "mood_table")
data class MoodEntity (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "mood") val mood: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "time_zone") val timeZone: String,
    @ColumnInfo(name = "memo") val memo: String,
)