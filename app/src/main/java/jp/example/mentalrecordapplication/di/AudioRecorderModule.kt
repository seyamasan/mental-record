package jp.example.mentalrecordapplication.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import jp.example.mentalrecordapplication.utils.AudioRecorderUtil

@Module
@InstallIn(ViewModelComponent::class)
object AudioRecorderModule {

    @Provides
    fun provideAudioRecorderUtil(): AudioRecorderUtil = AudioRecorderUtil()
}