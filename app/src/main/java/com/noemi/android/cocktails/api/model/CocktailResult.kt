package com.noemi.android.cocktails.api.model

import com.google.gson.annotations.SerializedName

data class CocktailResult(
    @SerializedName("drinks") val drinks: MutableList<Cocktail>
) {
    override fun toString(): String {
        return "CocktailResult: drinks=$drinks"
    }
}
