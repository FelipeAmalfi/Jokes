package com.jokes.di

import com.jokes.viewmodel.CategoriesViewModel
import com.jokes.viewmodel.SearchViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface  ApiComponent {
    fun inject(vm: SearchViewModel)
    fun inject(vm: CategoriesViewModel)
}