package com.bendenen.example.maplistexample.repository.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.bendenen.example.maplistexample.models.CarDescription

@Dao
interface CarDescriptionDao {

    @Query("SELECT * FROM cardescription")
    fun queryCarDescriptionList(): LiveData<List<CarDescription>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCarDescription(carDescription: CarDescription)
}