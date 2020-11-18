package com.jokes.di

import com.jokes.viewmodel.FavoriteViewModel
import dagger.Component

@Component(modules=[DatabaseModule::class])
interface DatabaseComponent {
    fun injectDatabase(vm: FavoriteViewModel)
}