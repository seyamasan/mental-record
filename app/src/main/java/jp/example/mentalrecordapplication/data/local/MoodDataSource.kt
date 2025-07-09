package jp.example.mentalrecordapplication.data.local

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
class MoodDataSource@Inject constructor(
    private val dao: MoodDao
): MoodDataSourceInterface {
    // DBにデータを保存
    override suspend fun insert(entity: MoodEntity): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                dao.insert(entity)
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
                dao.deleteById(id = id)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    // DBのデータ全部取得
    override suspend fun selectAll(): List<MoodEntity>? {
        return withContext(Dispatchers.IO) {
            val data = dao.selectAll()
            data.ifEmpty { null }
        }
    }

    // DBの内容を全て消す
    override suspend fun deleteAll(): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                dao.deleteAll()
                true
            } catch (e: Exception) {
                false
            }
        }
    }
}