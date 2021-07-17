package com.noemi.android.cocktails.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.noemi.android.cocktails.R
import com.noemi.android.cocktails.api.model.Cocktail
import com.noemi.android.cocktails.databinding.ItemCocktailBinding
import com.noemi.android.cocktails.util.OnTimeClickListener

class CocktailVH(
    private val binding: ItemCocktailBinding,
    private val cocktailListener: CocktailListener?,
    private val cocktailLaunchListener: CocktailLaunchListener?
) : RecyclerView.ViewHolder(binding.root) {

    fun bindCocktail(cocktail: Cocktail) {
        binding.apply {

            Glide.with(ivCocktail.context)
                .load(cocktail.icon)
                .error(R.drawable.gin_tonic)
                .placeholder(R.drawable.gin_tonic)
                .into(ivCocktail)

            tvCocktailTag.text = cocktail.name

            ivFavorite.setOnClickListener(object : OnTimeClickListener() {
                override fun oneTimeClick(view: View) {
                    cocktailListener?.invoke(cocktail)
                }
            })

            clContainer.setOnClickListener(object : OnTimeClickListener() {
                override fun oneTimeClick(view: View) {
                    cocktailLaunchListener?.invoke(cocktail)
                }
            })
        }
    }
}