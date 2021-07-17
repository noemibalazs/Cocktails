package com.noemi.android.cocktails.di

import com.noemi.android.cocktails.cocktails.CocktailsFragment
import com.noemi.android.cocktails.favorite.FavoriteFragment
import com.noemi.android.cocktails.landing.MainActivity
import com.noemi.android.cocktails.app.App
import com.noemi.android.cocktails.details.CocktailDetailsActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class, NetworkModule::class,
        RemoteDataSourceModule::class, ViewModelModule::class, LocalDataSourceModule::class, PreferencesModule::class]
)
interface AppComponent {

    fun inject(app: App)

    fun inject(main: MainActivity)

    fun inject(cocktailsFragment: CocktailsFragment)

    fun inject(favoriteFragment: FavoriteFragment)

    fun inject(cocktailDetailsActivity: CocktailDetailsActivity)
}