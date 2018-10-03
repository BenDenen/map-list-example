package com.bendenen.example.maplistexample.di.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.bendenen.example.maplistexample.di.ViewModelKey
import com.bendenen.example.maplistexample.mvvm.ViewModelFactory
import com.bendenen.example.maplistexample.screens.list.viewmodel.CarDescriptionsListViewModel
import com.bendenen.example.maplistexample.screens.main.viewmodel.CarDescriptionsMapViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CarDescriptionsMapViewModel::class)
    internal abstract fun bindCarDescriptionsMapViewModel(carDescriptionsViewModel: CarDescriptionsMapViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CarDescriptionsListViewModel::class)
    internal abstract fun bindCarDescriptionsListViewModel(carDescriptionsViewModel: CarDescriptionsListViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}