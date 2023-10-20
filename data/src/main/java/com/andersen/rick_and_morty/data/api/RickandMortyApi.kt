package com.andersen.rick_and_morty.data.api

import com.andersen.rick_and_morty.data.model.CharacterDTO
import com.andersen.rick_and_morty.data.model.CharactersDTO
import com.andersen.rick_and_morty.data.model.EpisodeDTO
import com.andersen.rick_and_morty.data.model.EpisodesDTO
import com.andersen.rick_and_morty.data.model.LocationDTO
import com.andersen.rick_and_morty.data.model.LocationsDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

internal interface RickandMortyApi {

    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("species") species: String,
        @Query("type") type: String,
        @Query("status") status: String,
        @Query("gender") gender: String,
    ): CharactersDTO

    @GET("character/{id}")
    suspend fun getCharacter(
        @Path("id") id: Int
    ): CharacterDTO


    @GET("location")
    suspend fun getLocations(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("type") type: String,
        @Query("dimension") dimension: String,
    ): LocationsDTO

    @GET("location/{id}")
    suspend fun getLocation(
        @Path("id") id: Int
    ): LocationDTO

    @GET("episode")
    suspend fun getEpisodes(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("episode") episode: String,
        @Query("air_date") airDate: String,
    ): EpisodesDTO

    @GET("episode/{id}")
    suspend fun getEpisode(
        @Path("id") id: Int
    ): EpisodeDTO

}