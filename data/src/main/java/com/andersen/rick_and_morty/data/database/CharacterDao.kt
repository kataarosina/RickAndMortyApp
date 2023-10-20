package com.andersen.rick_and_morty.data.database

import androidx.room.*
import com.andersen.rick_and_morty.data.model.CharacterEntity

@Dao
internal interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(characters: List<CharacterEntity>)

    @Update
    suspend fun update(character: CharacterEntity)

    @Delete
    suspend fun delete(character: CharacterEntity)

    @Query("SELECT * from character_table WHERE id = :id")
    suspend fun getCharacter(id: Int): CharacterEntity

    @Query("SELECT * FROM character_table WHERE (:species = '' OR species = :species) AND (:status = '' OR status = :status) AND (:gender = '' OR gender = :gender) ORDER BY name ASC")
    suspend fun getCharacters(
        species: String,
        status: String,
        gender: String
    ): List<CharacterEntity>

    @Query("SELECT * FROM character_table WHERE id IN (:characterIds) ORDER BY name ASC")
    suspend fun getCharactersByListId(characterIds: List<Int>): List<CharacterEntity>
}