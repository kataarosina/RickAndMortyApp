package com.andersen.rick_and_morty

import android.app.Application
import com.andersen.rick_and_morty.data.koin.dataModule
import com.andersen.rick_and_morty.koin.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RickAndMortyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RickAndMortyApplication)
            modules(
                dataModule, viewModelsModule

            )
        }
    }
}