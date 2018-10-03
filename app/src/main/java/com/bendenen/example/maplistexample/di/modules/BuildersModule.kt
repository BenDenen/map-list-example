package com.bendenen.example.maplistexample.di.modules

import com.bendenen.example.maplistexample.screens.list.CarDescriptionsListActivity
import com.bendenen.example.maplistexample.screens.main.CarDescriptionsMapActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeCarDescriptionsMapActivity(): CarDescriptionsMapActivity

    @ContributesAndroidInjector
    abstract fun contributeCarDescriptionsListActivity(): CarDescriptionsListActivity
}