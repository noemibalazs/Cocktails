package com.noemi.android.cocktails.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cocktail(
    @SerializedName("idDrink") val id: Int,
    @SerializedName("strDrink") val name: String,
    @SerializedName("strCategory") val category: String,
    @SerializedName("strInstructions") val instruction: String,
    @SerializedName("strDrinkThumb") val icon: String,
    @SerializedName("strIngredient1") val ingredient1: String?,
    @SerializedName("strIngredient2") val ingredient2: String?,
    @SerializedName("strIngredient3") val ingredient3: String?
) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Cocktail

        if (id != other.id) return false
        if (name != other.name) return false
        if (category != other.category) return false
        if (instruction != other.instruction) return false
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
        result = 31 * result + instruction.hashCode()
        result = 31 * result + icon.hashCode()
        result = 31 * result + (ingredient1?.hashCode() ?: 0)
        result = 31 * result + (ingredient2?.hashCode() ?: 0)
        result = 31 * result + (ingredient3?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Cocktail: id=$id, name='$name', category='$category', instruction='$instruction', icon='$icon', ingredient1=$ingredient1, ingredient2=$ingredient2, ingredient3=$ingredient3"
    }
}