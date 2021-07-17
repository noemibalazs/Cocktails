package com.noemi.android.cocktails.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.noemi.android.cocktails.COCKTAIL_TABLE

@Entity(tableName = COCKTAIL_TABLE)
data class CocktailEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val category: String,
    val instructions: String,
    val icon: String,
    val ingredient1: String,
    val ingredient2: String,
    val ingredient3: String
) {
    override fun toString(): String {
        return "CocktailEntity:id=$id, name='$name', category='$category', instructions='$instructions', icon='$icon', ingredient1='$ingredient1', ingredient2='$ingredient2', ingredient3='$ingredient3'"
    }
}