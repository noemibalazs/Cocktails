package com.noemi.android.cocktails.cocktails

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
import com.noemi.android.cocktails.databinding.FragmentCocktailsBinding
import com.noemi.android.cocktails.details.CocktailDetailsActivity
import com.noemi.android.cocktails.mapper.Mapper
import com.noemi.android.cocktails.preferences.PreferencesRepository
import com.noemi.android.cocktails.util.showErrorToastToUser
import com.noemi.android.cocktails.viewModel.CocktailViewModel
import javax.inject.Inject

class CocktailsFragment : Fragment() {

    @Inject
    lateinit var viewModel: CocktailViewModel

    @Inject
    lateinit var preferencesRepository: PreferencesRepository

    @Inject
    lateinit var mapper: Mapper

    private lateinit var binding: FragmentCocktailsBinding

    private val cocktailsAdapter: CocktailsAdapter by lazy {
        CocktailsAdapter(cocktailListener, cocktailLaunchListener)
    }

    private val cocktailListener: CocktailListener = {
        val cocktailEntity = mapper.mapCocktailsToEntity(it)
        viewModel.addCocktailToDB(cocktailEntity)
    }

    private val cocktailLaunchListener: CocktailLaunchListener = {
        preferencesRepository.cocktailId = it.id
        launchDetailsActivity()
    }

    private fun launchDetailsActivity() {
        val intent = Intent(activity, CocktailDetailsActivity::class.java)
        intent.apply {
            putExtra(CocktailDetailsActivity.KEY_COCKTAILS, CocktailDetailsActivity.COCKTAILS)
        }
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cocktails, container, false)
        App.instance.component.inject(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBinding()
        initObservers()
    }

    private fun initBinding() {
        Log.d(TAG, "initBinding()")

        binding.cocktailViewModel = viewModel
        binding.rvCocktails.adapter = cocktailsAdapter
    }

    private fun initObservers() {
        Log.d(TAG, "initObservers()")

        viewModel.defaultCocktails.observe(viewLifecycleOwner, {
            cocktailsAdapter.submitList(it)
        })

        viewModel.progressEvent.observe(viewLifecycleOwner, {
            binding.pbCocktails.isVisible = it ?: false
        })

        viewModel.errorEvent.observe(viewLifecycleOwner, {
            requireContext().showErrorToastToUser(it)
        })

        viewModel.onSearchClickEvent.observe(viewLifecycleOwner, { event ->
            onSearchClicked(event)
        })

        viewModel.searchedCocktails.observe(viewLifecycleOwner, {
            cocktailsAdapter.submitList(null)
            cocktailsAdapter.submitList(it)
        })

        viewModel.cocktailNameLengthError.observe(viewLifecycleOwner, {
            requireContext().showErrorToastToUser(it)
        })

        viewModel.emptySearchObserver.observe(viewLifecycleOwner, { empty ->
            if (empty)
                requireContext().showErrorToastToUser(getString(R.string.txt_try_it_again))
        })
    }

    private fun onSearchClicked(event: Boolean) {
        if (event) {
            when {
                lengthIsValid() -> {
                    viewModel.loadSearchedCocktails(getCocktailsName())
                    clearCocktailName()
                }
                else -> {
                    viewModel.cocktailNameLengthError.value =
                        getString(R.string.txt_search_error_toast)
                }
            }

        }
    }

    private fun lengthIsValid(): Boolean =
        binding.searchForCocktails.text.toString().trimEnd().length > 2

    private fun getCocktailsName() = binding.searchForCocktails.text.toString().trimEnd()

    private fun clearCocktailName() = binding.searchForCocktails.setText("")

    companion object {

        @JvmStatic
        fun newInstance(): CocktailsFragment {
            return CocktailsFragment()
        }

        private const val TAG = "CocktailsFragment"
    }
}