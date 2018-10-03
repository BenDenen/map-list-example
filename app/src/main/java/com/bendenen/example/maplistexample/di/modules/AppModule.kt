package com.bendenen.example.maplistexample.di.modules

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import com.bendenen.example.maplistexample.repository.database.CarDescriptionDao
import com.bendenen.example.maplistexample.repository.database.Database
import com.bendenen.example.maplistexample.utils.Utils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: Application) {

    @Provides
    @Singleton
    fun provideApplication(): Application = app

    @Provides
    @Singleton
    fun provideContext(): Context = app.applicationContext

    @Provides
    @Singleton
    fun providesUtils(): Utils = Utils(app)

    @Provides
    @Singleton
    fun provideCarDescriptionDatabase(app: Application): Database =
            Room.databaseBuilder(app, Database::class.java, "car_description_db").build()

    @Provides
    @Singleton
    fun provideCarDescriptionDao(db: Database): CarDescriptionDao = db.carDescriptionDao()
}