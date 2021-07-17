package com.noemi.android.cocktails.mapper

import com.noemi.android.cocktails.api.model.Cocktail
import com.noemi.android.cocktails.room.CocktailEntity
import javax.inject.Inject

class Mapper @Inject constructor() {

    fun mapCocktailsToEntity(cocktail: Cocktail): CocktailEntity {
        return CocktailEntity(
            id = cocktail.id,
            name = cocktail.name,
            category = cocktail.category,
            instructions = cocktail.instruction,
            icon = cocktail.icon,
            ingredient1 = cocktail.ingredient1 ?: "",
            ingredient2 = cocktail.ingredient2 ?: "",
            ingredient3 = cocktail.ingredient3 ?: ""
        )
    }

    fun mapCocktailEntityToCocktail(cocktailEntity: CocktailEntity): Cocktail {
        return Cocktail(
            id = cocktailEntity.id,
            name = cocktailEntity.name,
            category = cocktailEntity.category,
            instruction = cocktailEntity.instructions,
            icon = cocktailEntity.icon,
            ingredient1 = cocktailEntity.ingredient1,
            ingredient2 = cocktailEntity.ingredient2,
            ingredient3 = cocktailEntity.ingredient3
        )
    }
}