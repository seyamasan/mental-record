package com.example.mentalrecordapplication.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MoodEntity::class], version = 1, exportSchema = false)
abstract class MoodDataBase: RoomDatabase() {
    abstract  fun moodDao(): MoodDao

    companion object {
        fun buildDatabase(context: Context): MoodDataBase {
            return Room.databaseBuilder(
                context,
                MoodDataBase::class.java,
                "mood_database"
            ).build()
        }
    }
}