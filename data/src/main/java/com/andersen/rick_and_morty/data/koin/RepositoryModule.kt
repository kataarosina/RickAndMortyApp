package com.andersen.rick_and_morty.data.koin

import com.andersen.rick_and_morty.domain.repository.*
import com.andersen.rick_and_morty.data.repository.*
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val characterRemoteRepositoryModule = module {
    singleOf(::CharacterRemoteRepositoryImpl) { bind<CharacterRemoteRepository>() }
}
internal val characterLocalRepositoryModule = module {
    singleOf(::CharacterLocalRepositoryImpl) { bind<CharacterLocalRepository>() }
}


internal val locationRemoteRepositoryModule = module {
    singleOf(::LocationRemoteRepositoryImpl) { bind<LocationRemoteRepository>() }
}
internal val locationLocalRepositoryModule = module {
    singleOf(::LocationLocalRepositoryImpl) { bind<LocationLocalRepository>() }
}

internal val episodeRemoteRepositoryModule = module {
    singleOf(::EpisodeRemoteRepositoryImpl) { bind<EpisodeRemoteRepository>() }
}
internal val episodeLocalRepositoryModule = module {
    singleOf(::EpisodeLocalRepositoryImpl) { bind<EpisodeLocalRepository>() }
}


