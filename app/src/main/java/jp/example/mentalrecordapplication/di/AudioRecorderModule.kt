package jp.example.mentalrecordapplication.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import jp.example.mentalrecordapplication.utils.AudioUtil

@Module
@InstallIn(ViewModelComponent::class)
object AudioRecorderModule {

    @Provides
    fun provideAudioRecorderUtil(): AudioUtil = AudioUtil()
}