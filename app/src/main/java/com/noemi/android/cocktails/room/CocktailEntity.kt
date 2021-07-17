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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CocktailEntity

        if (id != other.id) return false
        if (name != other.name) return false
        if (category != other.category) return false
        if (instructions != other.instructions) return false
        if (icon != other.icon) return false
        if (ingredient1 != other.ingredient1) return false
        if (ingredient2 != other.ingredient2) return false
        if (ingredient3 != other.ingredient3) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + category.hashCode()
        result = 31 * result + instructions.hashCode()
        result = 31 * result + icon.hashCode()
        result = 31 * result + ingredient1.hashCode()
        result = 31 * result + ingredient2.hashCode()
        result = 31 * result + ingredient3.hashCode()
        return result
    }
}