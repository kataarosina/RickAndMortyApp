package com.andersen.rick_and_morty.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.andersen.rick_and_morty.data.model.LocationEntity

@Dao
internal interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(locations: List<LocationEntity>)

    @Update
    suspend fun update(location: LocationEntity)

    @Delete
    suspend fun delete(location: LocationEntity)

    @Query("SELECT * from location_table WHERE id = :id")
    suspend fun getLocation(id: Int): LocationEntity?


    @Query("SELECT * from location_table order by name asc")
    suspend fun getLocations(): List<LocationEntity>

    @Query("SELECT * FROM location_table WHERE (:type = '' OR type = :type) AND (:dimension = '' OR dimension = :dimension) ORDER BY name ASC")
    suspend fun getLocations(type: String, dimension: String): List<LocationEntity>


}