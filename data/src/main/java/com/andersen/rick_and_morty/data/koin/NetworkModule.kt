package com.andersen.rick_and_morty.data.koin

import com.andersen.rick_and_morty.BuildConfig
import com.andersen.rick_and_morty.data.api.RickandMortyApi
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

internal val networkModule = module {


    single(named("retrofitCard")) {
        Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    single { get<Retrofit>(named("retrofitCard")).create<RickandMortyApi>() }
}