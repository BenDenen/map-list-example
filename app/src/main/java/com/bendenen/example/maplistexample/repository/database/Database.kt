package com.bendenen.example.maplistexample.repository.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.bendenen.example.maplistexample.models.CarDescription

@Database(entities = [CarDescription::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun carDescriptionDao(): CarDescriptionDao
}