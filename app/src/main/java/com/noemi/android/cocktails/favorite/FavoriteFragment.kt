package com.noemi.android.cocktails.favorite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.noemi.android.cocktails.R
import com.noemi.android.cocktails.adapter.CocktailLaunchListener
import com.noemi.android.cocktails.adapter.CocktailListener
import com.noemi.android.cocktails.adapter.CocktailsAdapter
import com.noemi.android.cocktails.app.App
import com.noemi.android.cocktails.databinding.FragmentFavoritesBinding
import com.noemi.android.cocktails.details.CocktailDetailsActivity
import com.noemi.android.cocktails.mapper.Mapper
import com.noemi.android.cocktails.preferences.PreferencesRepository
import com.noemi.android.cocktails.util.CocktailAvatarHide
import com.noemi.android.cocktails.viewModel.CocktailViewModel
import javax.inject.Inject

class FavoriteFragment : Fragment() {

    @Inject
    lateinit var cocktailViewModel: CocktailViewModel

    @Inject
    lateinit var mapper: Mapper

    @Inject
    lateinit var preferencesRepository: PreferencesRepository

    private lateinit var binding: FragmentFavoritesBinding
    private val avatarHide = CocktailAvatarHide(hide = true)

    private val cocktailsAdapter: CocktailsAdapter by lazy {
        CocktailsAdapter(cocktailListener, cocktailLaunchListener, avatarHide)
    }

    private val cocktailListener: CocktailListener = {
        Log.d(TAG, "cocktailListener clicked")
    }

    private val cocktailLaunchListener: CocktailLaunchListener = {
        preferencesRepository.cocktailId = it.id
        launchDetailsActivity()
    }

    private fun launchDetailsActivity() {
        val intent = Intent(requireContext(), CocktailDetailsActivity::class.java)
        intent.apply {
            putExtra(CocktailDetailsActivity.KEY_FAVORITE, CocktailDetailsActivity.FAVORITE)
        }
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        App.instance.component.inject(this)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBinding()
        cocktailViewModel.getCocktailsFromDB(mapper)
        initObservers()

    }

    private fun initBinding() {
        binding.rvFavoriteCocktails.adapter = cocktailsAdapter
    }

    private fun initObservers() {
        cocktailViewModel.localCocktailsObserver.observe(viewLifecycleOwner, {
            cocktailsAdapter.submitList(null)
            cocktailsAdapter.submitList(it)
        })

        cocktailViewModel.progressEvent.observe(viewLifecycleOwner, {
            binding.pbFavoriteCocktails.isVisible = it ?: false
        })
    }

    companion object {

        @JvmStatic
        fun newInstance(): FavoriteFragment {
            return FavoriteFragment()
        }

        private const val TAG = "FavoriteFragment"
    }
}