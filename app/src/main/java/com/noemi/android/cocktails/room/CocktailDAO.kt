package com.noemi.android.cocktails.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CocktailDAO {

    @Query("SELECT * FROM cocktail_table")
    fun getCocktails(): MutableList<CocktailEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCocktail(cocktail: CocktailEntity)

    @Query("SELECT * FROM COCKTAIL_TABLE WHERE id=:ckID")
    fun getCocktailById(ckID: Int): CocktailEntity
}