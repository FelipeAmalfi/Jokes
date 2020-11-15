package com.jokes.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jokes.api.JokeApiService
import com.jokes.model.Joke
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class CategoriesViewModel: ViewModel() {
    private val jokeService =  JokeApiService()
    private val disposable = CompositeDisposable()

    val categories = MutableLiveData<List<String>>()
    val categoryLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()


    fun getCategories(){
        categories.value = arrayListOf()
        loading.value = true
        categoryLoadError.value =  false
        fetchCategories()
    }


    private fun fetchCategories(){
        disposable.add(
                jokeService.fetchCategory()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object: DisposableSingleObserver<List<String>>(){
                            override fun onSuccess(result: List<String>) {
                                categories.value = result
                                categoryLoadError.value =  false
                                loading.value = false
                            }
                            override fun onError(e: Throwable) {
                                e.printStackTrace()
                                categoryLoadError.value =  true
                                loading.value = false
                            }

                        })
        )
    }


}