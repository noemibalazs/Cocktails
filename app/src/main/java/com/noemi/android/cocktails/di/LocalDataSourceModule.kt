package com.noemi.android.cocktails.di

import com.noemi.android.cocktails.api.localDataSource.CocktailLocalRepository
import com.noemi.android.cocktails.api.localDataSource.CocktailLocalService
import com.noemi.android.cocktails.room.CocktailDAO
import com.noemi.android.cocktails.room.CocktailDB
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalDataSourceModule {

    @Provides
    @Singleton
    fun provideCocktailDAO(cocktailDB: CocktailDB) = cocktailDB.cocktailDAO()

    @Provides
    @Singleton
    fun provideCocktailLocalRepository(cocktailDAO: CocktailDAO): CocktailLocalService =
        CocktailLocalRepository(cocktailDAO)
}