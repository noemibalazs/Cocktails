package com.noemi.android.cocktails.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.noemi.android.cocktails.api.localDataSource.CocktailLocalService
import com.noemi.android.cocktails.api.model.Cocktail
import com.noemi.android.cocktails.api.model.CocktailResult
import com.noemi.android.cocktails.api.remoteDataSource.CocktailRemoteService
import com.noemi.android.cocktails.mapper.Mapper
import com.noemi.android.cocktails.room.CocktailEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import javax.inject.Inject

class CocktailViewModel @Inject constructor(
    private val cocktailRemoteService: CocktailRemoteService,
    private val cocktailLocalService: CocktailLocalService
) :
    BaseCocktailViewModel() {

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val defaultCocktails = MutableLiveData<MutableList<Cocktail>>()
    val searchedCocktails = MutableLiveData<MutableList<Cocktail>>()
    val onSearchClickEvent = MutableLiveData<Boolean>()
    val cocktailNameLengthError = MutableLiveData<String>()
    val cocktailEntityObserver = MutableLiveData<CocktailEntity?>()
    val cocktailObserver = MutableLiveData<Cocktail>()
    val localCocktailsObserver = MutableLiveData<MutableList<Cocktail>>()
    val emptySearchObserver = MutableLiveData<Boolean>()

    init {
        loadDefaultCocktails()
    }

    private fun loadDefaultCocktails() {
        Log.d(TAG, "loadDefaultCocktails()")
        compositeDisposable.clear()
        val disposable = cocktailRemoteService.getCocktails()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                progressEvent.value = true
            }
            .doFinally {
                progressEvent.value = false
            }
            .subscribeWith(object : DisposableSingleObserver<CocktailResult>() {
                override fun onSuccess(result: CocktailResult) {
                    defaultCocktails.value = result.drinks
                    Log.d(TAG, "loadDefaultCocktails() - onSuccess() - ${result.drinks.size}")
                }

                override fun onError(throwable: Throwable) {
                    errorEvent.value = throwable.message ?: ERROR_MESSAGE
                    Log.e(TAG, "loadDefaultCocktails() - onError()")
                }
            })
        compositeDisposable.add(disposable)
    }

    fun addCocktailToDB(cocktailEntity: CocktailEntity) {
        Log.d(TAG, "addCocktailToDB() - id: ${cocktailEntity.id}")
        CoroutineScope(Dispatchers.IO).launch {
            cocktailLocalService.addCocktail(cocktailEntity)
        }
    }

    fun onSearchClicked() {
        Log.d(TAG, "onSearchClicked()")
        onSearchClickEvent.value = true
    }

    fun loadSearchedCocktails(name: String) {
        Log.d(TAG, "loadSearchedCocktails() - name: $name")
        compositeDisposable.clear()
        val disposable = cocktailRemoteService.getSearchedCocktails(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                progressEvent.value = true
            }
            .doFinally {
                progressEvent.value = false
            }
            .subscribeWith(object : DisposableSingleObserver<CocktailResult>() {
                override fun onSuccess(result: CocktailResult) {
                    emptySearchObserver.value = result.drinks.isNullOrEmpty()
                    searchedCocktails.value = result.drinks
                    Log.d(TAG, "loadSearchedCocktails() - onSuccess()")
                }

                override fun onError(throwable: Throwable) {
                    errorEvent.value = throwable.message ?: ERROR_MESSAGE
                    Log.e(TAG, "loadSearchedCocktails() - onError()")
                }
            })
        compositeDisposable.add(disposable)
    }

    fun getCocktail(id: Int) {
        Log.d(TAG, "getCocktail()")
        compositeDisposable.clear()
        val disposable = cocktailRemoteService.getCocktailDetails(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                progressEvent.value = true
            }
            .doFinally {
                progressEvent.value = false
            }
            .subscribeWith(object : DisposableSingleObserver<CocktailResult>() {
                override fun onSuccess(result: CocktailResult) {
                    cocktailObserver.value = result.drinks[0]
                    Log.d(TAG, "getRemoteCocktail() - onSuccess()")
                }

                override fun onError(throwable: Throwable) {
                    errorEvent.value = throwable.message ?: ERROR_MESSAGE
                    Log.e(TAG, "getRemoteCocktail() - onError()")
                }
            })
        compositeDisposable.add(disposable)
    }

    fun getCocktailsFromDB(mapper: Mapper) {
        Log.d(TAG, "getLocalCocktails()")
        progressEvent.value = true
        CoroutineScope(Dispatchers.IO).launch {
            val cocktails = cocktailLocalService.getCocktails()
            withContext(Dispatchers.Main) {
                localCocktailsObserver.value = mapCocktailEntitiesToCocktails(mapper, cocktails)
                progressEvent.value = false
            }
        }
    }

    private fun mapCocktailEntitiesToCocktails(
        mapper: Mapper,
        entities: MutableList<CocktailEntity>
    ): MutableList<Cocktail> {
        val cocktails = mutableListOf<Cocktail>()
        entities.forEach {
            cocktails.add(mapper.mapCocktailEntityToCocktail(it))
        }
        return cocktails
    }

    fun getCocktailEntity(id: Int) {
        Log.d(TAG, "getCocktailEntity()")
        CoroutineScope(Dispatchers.IO).launch {
            val entity = cocktailLocalService.getCocktailById(id)
            withContext(Dispatchers.Main) {
                cocktailEntityObserver.value = entity
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val cocktailRemoteService: CocktailRemoteService,
        private val cocktailLocalService: CocktailLocalService
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CocktailViewModel(cocktailRemoteService, cocktailLocalService) as T
        }
    }

    companion object {

        private const val ERROR_MESSAGE = "An error has occurred!"
        private const val TAG = "CocktailViewModel"
    }
}