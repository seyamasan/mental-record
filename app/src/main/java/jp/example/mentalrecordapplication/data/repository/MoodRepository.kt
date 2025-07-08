package jp.example.mentalrecordapplication.data.repository

import jp.example.mentalrecordapplication.data.local.room.MoodDao
import jp.example.mentalrecordapplication.data.local.room.MoodEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/*
* mood_databaseのmood_tableを操作する
*/

@Singleton
class MoodRepository@Inject constructor(
    private val moodDao: MoodDao
): MoodRepositoryInterface {

    // DBにデータを保存
    override suspend fun insert(mood:String, date:String, timeZone: String, memo: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
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
    }

    // 指定したidのデータを削除
    override suspend fun deleteById(id: Int): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                moodDao.deleteById(id = id)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    // DBのデータ全部取得
    override suspend fun selectAll(): List<MoodEntity>? {
        return withContext(Dispatchers.IO) {
            val data = moodDao.selectAll()
            data.ifEmpty { null }
        }
    }

    // DBの内容を全て消す
    override suspend fun deleteAll(): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                moodDao.deleteAll()
                true
            } catch (e: Exception) {
                false
            }
        }
    }
}