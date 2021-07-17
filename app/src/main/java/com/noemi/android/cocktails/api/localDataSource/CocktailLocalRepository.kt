package com.noemi.android.cocktails.api.localDataSource

import com.noemi.android.cocktails.room.CocktailDAO
import com.noemi.android.cocktails.room.CocktailEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CocktailLocalRepository @Inject constructor(private val cocktailDAO: CocktailDAO) :
    CocktailLocalService {

    override fun getCocktails(): MutableList<CocktailEntity> {
        return cocktailDAO.getCocktails()
    }

    override fun addCocktail(cocktail: CocktailEntity) {
        return cocktailDAO.addCocktail(cocktail)
    }

    override fun getCocktailById(ckID: Int): CocktailEntity {
        return cocktailDAO.getCocktailById(ckID)
    }
}