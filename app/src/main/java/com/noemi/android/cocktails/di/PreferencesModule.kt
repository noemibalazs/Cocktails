package com.noemi.android.cocktails.di

import android.content.SharedPreferences
import com.noemi.android.cocktails.preferences.PreferencesRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PreferencesModule {

    @Singleton
    @Provides
    fun providePreferenceRepository(sharedPreferences: SharedPreferences) = PreferencesRepository(sharedPreferences)
}