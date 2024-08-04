package com.example.mentalrecordapplication.repository

import android.app.Application
import com.example.mentalrecordapplication.room.MoodDao
import com.example.mentalrecordapplication.room.MoodDataBase
import com.example.mentalrecordapplication.room.MoodEntity

/*
* mood_databaseのmood_tableを操作する
*/

class MoodRepository(application: Application) {
    private val _dao: MoodDao
    init {
        val db = MoodDataBase.buildDatabase(application)
        _dao = db.moodDao() // 使うDaoを指定
    }

    // DBにデータを保存
    suspend fun insert(mood:String, date:String, timeZone: String, memo: String): Boolean {
        return try {
            _dao.insert(
                MoodEntity(
                    id = 0, // 自動的にIDを入れるときは0を入れる
                    mood = mood,
                    date = date,
                    timeZone = timeZone,
                    memo = memo
                )
            )
            true
        } catch (e: Exception) {
            false
        }
    }

    // DBのデータ全部取得
    suspend fun selectAll(): List<MoodEntity>? {
        val data = _dao.selectAll()
        data.ifEmpty {
            return null
        }
        return data
    }

    // DBの内容を全て消す
    suspend fun deleteAll(): Boolean {
        return try {
            _dao.deleteAll()
            true
        } catch (e: Exception) {
            false
        }
    }
}