package com.jokes.di

import com.jokes.viewmodel.JokeViewModel
import dagger.Component

@Component(modules = [ApiModule::class, DatabaseModule::class])
interface ApiDatabaseComponent {
        fun inject(vm: JokeViewModel)
}