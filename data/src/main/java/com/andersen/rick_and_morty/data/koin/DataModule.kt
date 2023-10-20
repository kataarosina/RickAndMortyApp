package com.andersen.rick_and_morty.data.koin

import org.koin.dsl.module

val dataModule = module {
    includes(
        rickAndMortyDatabaseModule,
        characterRemoteRepositoryModule,
        characterLocalRepositoryModule,
        locationRemoteRepositoryModule,
        locationLocalRepositoryModule,
        episodeRemoteRepositoryModule,
        episodeLocalRepositoryModule,
        networkModule,
        useCaseModule,
    )
}