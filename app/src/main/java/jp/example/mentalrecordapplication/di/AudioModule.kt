package jp.example.mentalrecordapplication.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import jp.example.mentalrecordapplication.utils.AudioUtil

@Module
@InstallIn(ViewModelComponent::class)
object AudioModule {

    @Provides
    fun provideAudioUtil(): AudioUtil = AudioUtil()
}