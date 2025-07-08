package jp.example.mentalrecordapplication.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MoodEntity::class], version = 1, exportSchema = false)
abstract class MoodDataBase: RoomDatabase() {
    abstract  fun moodDao(): MoodDao
}