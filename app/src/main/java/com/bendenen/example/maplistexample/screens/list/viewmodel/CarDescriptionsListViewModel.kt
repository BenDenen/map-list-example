package com.bendenen.example.maplistexample.screens.list.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.bendenen.example.maplistexample.models.CarDescription
import com.bendenen.example.maplistexample.repository.CarDescriptionLiveDataProvider
import com.bendenen.example.maplistexample.repository.datasource.Resource
import javax.inject.Inject

class CarDescriptionsListViewModel @Inject constructor(private val liveDataProvider: CarDescriptionLiveDataProvider) : ViewModel() {

    val carDescriptions: LiveData<Resource<List<CarDescription>>> = liveDataProvider.getCarDescriptionList()

}