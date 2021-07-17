package com.noemi.android.cocktails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.noemi.android.cocktails.R
import com.noemi.android.cocktails.api.model.Cocktail
import com.noemi.android.cocktails.databinding.ItemCocktailBinding
import com.noemi.android.cocktails.util.CocktailAvatarHide

typealias CocktailListener = (Cocktail) -> Unit
typealias CocktailLaunchListener = (Cocktail) -> Unit

class CocktailsAdapter(
    private val cocktailListener: CocktailListener,
    private val cocktailLaunchListener: CocktailLaunchListener,
    private val cocktailAvatarHide: CocktailAvatarHide
) : ListAdapter<Cocktail, CocktailVH>(CocktailDifUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemCocktailBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_cocktail, parent, false)
        return CocktailVH(binding, cocktailListener, cocktailLaunchListener, cocktailAvatarHide)
    }

    override fun onBindViewHolder(holder: CocktailVH, position: Int) {
        holder.bindCocktail(getItem(position))
    }
}