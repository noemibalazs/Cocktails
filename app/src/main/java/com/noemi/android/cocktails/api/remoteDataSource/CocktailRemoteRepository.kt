package com.noemi.android.cocktails.api.remoteDataSource

import com.noemi.android.cocktails.api.dataSource.CocktailAPI
import com.noemi.android.cocktails.api.model.CocktailResult
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CocktailRemoteRepository @Inject constructor(private val cocktailAPI: CocktailAPI) :
    CocktailRemoteService {

    override fun getCocktails(): Single<CocktailResult> {
        return cocktailAPI.getCocktails()
    }

    override fun getSearchedCocktails(search: String): Single<CocktailResult> {
        return cocktailAPI.getSearchedCocktails(search)
    }

    override fun getCocktailDetails(id: Int): Single<CocktailResult> {
        return cocktailAPI.getCocktailDetails(id)
    }
}