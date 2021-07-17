package com.noemi.android.cocktails.api.dataSource

import com.noemi.android.cocktails.api.model.CocktailResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailAPI {

    @GET("search.php?s=margarita")
    fun getCocktails(): Single<CocktailResult>

    @GET("search.php?")
    fun getSearchedCocktails(@Query("s") search: String): Single<CocktailResult>

    @GET("lookup.php?")
    fun getCocktailDetails(@Query("i") id: Int): Single<CocktailResult>
}