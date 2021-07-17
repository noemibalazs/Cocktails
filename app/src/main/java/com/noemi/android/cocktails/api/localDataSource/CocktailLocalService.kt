package com.noemi.android.cocktails.api.localDataSource

import com.noemi.android.cocktails.room.CocktailEntity

interface CocktailLocalService {

    fun getCocktails(): MutableList<CocktailEntity>

    fun addCocktail(cocktail: CocktailEntity)

    fun getCocktailById(ckID: Int): CocktailEntity
}