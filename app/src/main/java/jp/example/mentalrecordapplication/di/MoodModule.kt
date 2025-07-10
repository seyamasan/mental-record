package jp.example.mentalrecordapplication.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.example.mentalrecordapplication.data.local.MoodDataSourceImpl
import jp.example.mentalrecordapplication.data.local.MoodDataSource
import jp.example.mentalrecordapplication.data.local.room.MoodDao
import jp.example.mentalrecordapplication.data.local.room.MoodDataBase
import jp.example.mentalrecordapplication.data.repository.MoodRepositoryImpl
import jp.example.mentalrecordapplication.data.repository.MoodRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MoodModule {

    @Provides
    @Singleton
    fun provideMoodDao(@ApplicationContext context: Context): MoodDao {
        return Room.databaseBuilder(
            context,
            MoodDataBase::class.java,
            "mood_database"
        ).build().moodDao()
    }

    @Provides
    @Singleton
    fun provideMoodDataSource(moodDao: MoodDao): MoodDataSource {
        return MoodDataSourceImpl(moodDao)
    }

    @Provides
    @Singleton
    fun provideMoodRepository(dataSource: MoodDataSource): MoodRepository {
        return MoodRepositoryImpl(dataSource)
    }
}