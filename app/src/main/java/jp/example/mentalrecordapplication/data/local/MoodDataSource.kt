package jp.example.mentalrecordapplication.data.local

import jp.example.mentalrecordapplication.data.local.room.MoodEntity

interface MoodDataSource {
    suspend fun insert(entity: MoodEntity): Boolean
    suspend fun deleteById(id: Int): Boolean
    suspend fun selectAll(): List<MoodEntity>?
    suspend fun deleteAll(): Boolean
}