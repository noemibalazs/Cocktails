package com.noemi.android.cocktails.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.noemi.android.cocktails.COCKTAIL_DB
import com.noemi.android.cocktails.room.CocktailDB
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    @ForApplication
    fun provideContext(): Context = application

    @Provides
    @Singleton
    fun provideCocktailDB(): CocktailDB =
        Room.databaseBuilder(application.applicationContext, CocktailDB::class.java, COCKTAIL_DB)
            .build()

    @Singleton
    @Provides
    fun provideSharedPreference(): SharedPreferences {
        return application.getSharedPreferences(application.packageName, Context.MODE_PRIVATE)
    }
}