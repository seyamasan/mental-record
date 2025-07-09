package jp.example.mentalrecordapplication.data.repository

import jp.example.mentalrecordapplication.data.local.MoodDataSourceInterface
import jp.example.mentalrecordapplication.data.local.room.MoodEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoodRepository@Inject constructor(
    private val dataSource: MoodDataSourceInterface
): MoodRepositoryInterface {
    override suspend fun insert(entity: MoodEntity): Boolean {
        return dataSource.insert(entity)
    }

    override suspend fun deleteById(id: Int): Boolean {
        return dataSource.deleteById(id = id)
    }

    override suspend fun selectAll(): List<MoodEntity>? {
        return dataSource.selectAll()
    }

    override suspend fun deleteAll(): Boolean {
        return dataSource.deleteAll()
    }
}