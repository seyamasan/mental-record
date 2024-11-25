package jp.example.mentalrecordapplication.di

import jp.example.mentalrecordapplication.room.MoodDataBase
import jp.example.mentalrecordapplication.room.MoodRepository
import jp.example.mentalrecordapplication.viewmodel.RecordMoodViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val moodModule = module {
    single { MoodDataBase.buildDatabase(androidContext()).moodDao() }
    single { MoodRepository(get()) }
    viewModel { RecordMoodViewModel(get()) }
}