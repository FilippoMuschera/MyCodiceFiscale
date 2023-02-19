package com.muschera.mycodicefiscale.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query



@Dao
interface DaoCity {


    @Query("SELECT * FROM city WHERE description LIKE :filter1 COLLATE NOCASE AND abbreviation LIKE :filter2 COLLATE NOCASE ORDER BY description LIMIT 1")
    fun getCity(filter1: String, filter2: String): City

    @Query("SELECT * FROM city")
    fun getAll(): LiveData<List<City>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSavedCF(toSavedCF: SavedCF)

    @Query("SELECT * FROM savedCF order by cf")
    fun getAllSavedCF(): LiveData<List<SavedCF>>

    @Query("DELETE FROM savedCF WHERE   cf = :filter1")
    fun deleteSavedCF(filter1: String)


}
