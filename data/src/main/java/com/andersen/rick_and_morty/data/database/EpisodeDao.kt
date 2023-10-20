package com.andersen.rick_and_morty.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.andersen.rick_and_morty.data.model.EpisodeEntity
import com.andersen.rick_and_morty.data.model.LocationEntity

@Dao
internal interface EpisodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(episodes: List<EpisodeEntity>)

    @Update
    suspend fun update(episode: EpisodeEntity)

    @Delete
    suspend fun delete(episode: EpisodeEntity)

    @Query("SELECT * from episode_table WHERE id = :id")
    suspend fun getEpisode(id: Int): EpisodeEntity

    @Query("SELECT * from episode_table WHERE (:episode = '' OR episode = :episode) AND (:airDate = '' OR air_date = :airDate) ORDER BY name ASC")
    suspend fun getEpisodes(episode: String, airDate: String): List<EpisodeEntity>

    @Query("SELECT * FROM episode_table WHERE id IN (:episodeIds) ORDER BY name ASC")
    suspend fun getEpisodes(episodeIds: List<Int>): List<EpisodeEntity>


}