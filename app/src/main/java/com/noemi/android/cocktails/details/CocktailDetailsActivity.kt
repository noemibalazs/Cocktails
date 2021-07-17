package com.noemi.android.cocktails.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.noemi.android.cocktails.R
import com.noemi.android.cocktails.api.model.Cocktail
import com.noemi.android.cocktails.app.App
import com.noemi.android.cocktails.databinding.ActivityCocktailDetailsBinding
import com.noemi.android.cocktails.preferences.PreferencesRepository
import com.noemi.android.cocktails.room.CocktailEntity
import com.noemi.android.cocktails.util.showToastToUser
import com.noemi.android.cocktails.viewModel.CocktailViewModel
import javax.inject.Inject

class CocktailDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var preferencesRepository: PreferencesRepository

    @Inject
    lateinit var cocktailViewModel: CocktailViewModel

    private lateinit var binding: ActivityCocktailDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.component.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cocktail_details)

        populateUIBasedOnIntent()
        initObserver()
    }

    private fun populateUIBasedOnIntent() {
        val cocktails = intent?.getStringExtra(KEY_COCKTAILS)
        if (cocktails == COCKTAILS) {
            cocktailViewModel.getCocktail(preferencesRepository.cocktailId)
        }
        val entities = intent?.getStringExtra(KEY_FAVORITE)
        if (entities == FAVORITE) {
            cocktailViewModel.getCocktailEntity(preferencesRepository.cocktailId)
        }
    }

    private fun initObserver() {
        Log.d(TAG, "initObserver()")

        cocktailViewModel.cocktailObserver.observe(this, {
            populateUIByCocktail(it)
        })

        cocktailViewModel.errorEvent.observe(this, {
            showToastToUser(it)
        })

        cocktailViewModel.progressEvent.observe(this, {
            binding.pbCocktailDetails.isVisible = it ?: false
        })

        cocktailViewModel.cocktailEntityObserver.observe(this, {
            it?.let { populateUIByEntity(it) }
        })
    }

    private fun populateUIByCocktail(cocktail: Cocktail) {
        Glide.with(this).load(cocktail.icon).error(R.drawable.gin_tonic)
            .placeholder(R.drawable.gin_tonic).into(binding.ivCocktailDetails)

        binding.tvCocktailName.text = cocktail.name
        binding.tvCocktailDescription.text = cocktail.instruction
        binding.tvCocktailCategory.text =
            String.format(getString(R.string.txt_category), cocktail.category)
        binding.tvCocktailIngredients.text = String.format(
            getString(R.string.txt_ingredients),
            cocktail.ingredient1,
            cocktail.ingredient2,
            cocktail.ingredient3
        )
    }

    private fun populateUIByEntity(cocktailEntity: CocktailEntity) {
        Glide.with(this).load(cocktailEntity.icon).error(R.drawable.gin_tonic)
            .placeholder(R.drawable.gin_tonic).into(binding.ivCocktailDetails)

        binding.tvCocktailName.text = cocktailEntity.name
        binding.tvCocktailDescription.text = cocktailEntity.instructions
        binding.tvCocktailCategory.text =
            String.format(getString(R.string.txt_category), cocktailEntity.category)
        binding.tvCocktailIngredients.text = String.format(
            getString(R.string.txt_ingredients),
            cocktailEntity.ingredient1,
            cocktailEntity.ingredient2,
            cocktailEntity.ingredient3
        )
    }

    companion object {

        private const val TAG = "CocktailDetailsActivity"
        const val KEY_FAVORITE = "key_favorite"
        const val KEY_COCKTAILS = "key_cocktails"
        const val COCKTAILS = "cocktails"
        const val FAVORITE = "favorite"
    }
}