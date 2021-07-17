package com.noemi.android.cocktails.di

import com.noemi.android.cocktails.api.localDataSource.CocktailLocalService
import com.noemi.android.cocktails.api.remoteDataSource.CocktailRemoteService
import com.noemi.android.cocktails.viewModel.CocktailViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelModule {

    @Provides
    @Singleton
    fun provideCocktailViewModel(
        cocktailRemoteService: CocktailRemoteService,
        cocktailLocalService: CocktailLocalService
    ) =
        CocktailViewModel.Factory(cocktailRemoteService, cocktailLocalService)
}