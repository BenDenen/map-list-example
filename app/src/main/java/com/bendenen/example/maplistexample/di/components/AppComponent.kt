package com.bendenen.example.maplistexample.di.components

import com.bendenen.example.maplistexample.MapListExampleApplication
import com.bendenen.example.maplistexample.di.modules.AppModule
import com.bendenen.example.maplistexample.di.modules.BuildersModule
import com.bendenen.example.maplistexample.di.modules.NetModule
import com.bendenen.example.maplistexample.di.modules.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, BuildersModule::class, ViewModelModule::class, AppModule::class, NetModule::class])
interface AppComponent {
    fun inject(app: MapListExampleApplication)
}