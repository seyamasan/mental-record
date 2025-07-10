package jp.example.mentalrecordapplication.data.repository

import jp.example.mentalrecordapplication.data.local.room.MoodEntity

interface MoodRepository {
    suspend fun insert(entity: MoodEntity): Boolean
    suspend fun deleteById(id: Int): Boolean
    suspend fun selectAll(): List<MoodEntity>?
    suspend fun deleteAll(): Boolean
}