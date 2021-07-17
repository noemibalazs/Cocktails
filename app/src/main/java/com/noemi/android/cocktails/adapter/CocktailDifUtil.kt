package com.noemi.android.cocktails.adapter

import androidx.recyclerview.widget.DiffUtil
import com.noemi.android.cocktails.api.model.Cocktail

class CocktailDifUtil : DiffUtil.ItemCallback<Cocktail>() {

    override fun areContentsTheSame(oldItem: Cocktail, newItem: Cocktail): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areItemsTheSame(oldItem: Cocktail, newItem: Cocktail): Boolean {
        return oldItem == newItem
    }
}