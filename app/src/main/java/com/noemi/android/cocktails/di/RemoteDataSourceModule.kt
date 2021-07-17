package com.noemi.android.cocktails.di

import com.noemi.android.cocktails.api.dataSource.CocktailAPI
import com.noemi.android.cocktails.api.remoteDataSource.CocktailRemoteRepository
import com.noemi.android.cocktails.api.remoteDataSource.CocktailRemoteService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteDataSourceModule {

    @Provides
    @Singleton
    fun provideCocktailRepository(cocktailAPI: CocktailAPI): CocktailRemoteService =
        CocktailRemoteRepository(cocktailAPI)
}