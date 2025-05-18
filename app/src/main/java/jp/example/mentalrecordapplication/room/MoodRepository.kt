package jp.example.mentalrecordapplication.room

import javax.inject.Inject
import javax.inject.Singleton

/*
* mood_databaseのmood_tableを操作する
*/

@Singleton
class MoodRepository@Inject constructor(
    private val moodDao: MoodDao
) {

    // DBにデータを保存
    suspend fun insert(mood:String, date:String, timeZone: String, memo: String): Boolean {
        return try {
            moodDao.insert(
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

    // 指定したidのデータを削除
    suspend fun deleteById(id: Int): Boolean {
        return try {
            moodDao.deleteById(id = id)
            true
        } catch (e: Exception) {
            false
        }
    }

    // DBのデータ全部取得
    suspend fun selectAll(): List<MoodEntity>? {
        val data = moodDao.selectAll()
        data.ifEmpty {
            return null
        }
        return data
    }

    // DBの内容を全て消す
    suspend fun deleteAll(): Boolean {
        return try {
            moodDao.deleteAll()
            true
        } catch (e: Exception) {
            false
        }
    }
}