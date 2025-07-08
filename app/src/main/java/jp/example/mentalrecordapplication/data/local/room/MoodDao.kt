package jp.example.mentalrecordapplication.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MoodDao {
    @Insert
    suspend fun insert(moodEntity: MoodEntity)

    @Query("DELETE FROM mood_table WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM mood_table")
    suspend fun selectAll(): List<MoodEntity>

    @Query("DELETE FROM mood_table")
    suspend fun deleteAll()
}