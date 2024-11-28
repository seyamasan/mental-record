package jp.example.mentalrecordapplication.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.example.mentalrecordapplication.room.MoodDao
import jp.example.mentalrecordapplication.room.MoodDataBase
import jp.example.mentalrecordapplication.room.MoodRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MoodModule {

    @Provides
    @Singleton
    fun provideMoodDao(application: Application): MoodDao {
        return MoodDataBase.buildDatabase(application.applicationContext).moodDao()
    }

    @Provides
    @Singleton
    fun provideMoodRepository(moodDao: MoodDao): MoodRepository {
        return MoodRepository(moodDao)
    }
}