package com.noemi.android.cocktails.api.remoteDataSource

import com.noemi.android.cocktails.api.model.CocktailResult
import io.reactivex.Single

interface CocktailRemoteService {

    fun getCocktails(): Single<CocktailResult>

    fun getSearchedCocktails(search: String): Single<CocktailResult>

    fun getCocktailDetails(id: Int): Single<CocktailResult>
}