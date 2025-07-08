package jp.example.mentalrecordapplication.data.repository

import jp.example.mentalrecordapplication.data.local.room.MoodEntity

interface MoodRepositoryInterface {
    suspend fun insert(mood:String, date:String, timeZone: String, memo: String): Boolean
    suspend fun deleteById(id: Int): Boolean
    suspend fun selectAll(): List<MoodEntity>?
    suspend fun deleteAll(): Boolean
}