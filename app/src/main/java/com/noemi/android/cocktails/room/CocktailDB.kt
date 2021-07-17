package com.noemi.android.cocktails.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CocktailEntity::class], exportSchema = false, version = 1)
abstract class CocktailDB : RoomDatabase() {

    abstract fun cocktailDAO(): CocktailDAO
}