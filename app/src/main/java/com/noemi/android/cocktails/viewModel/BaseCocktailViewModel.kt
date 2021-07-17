package com.noemi.android.cocktails.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseCocktailViewModel : ViewModel() {

    protected abstract val compositeDisposable: CompositeDisposable
    val errorEvent = MutableLiveData<String>()
    val progressEvent = MutableLiveData<Boolean>()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}