package com.bendenen.example.maplistexample

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.bendenen.example.maplistexample.models.CarDescription
import com.bendenen.example.maplistexample.repository.CarDescriptionLiveDataProvider
import com.bendenen.example.maplistexample.repository.datasource.Resource
import com.bendenen.example.maplistexample.screens.main.viewmodel.CarDescriptionsMapViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class CarDescriptionsViewModelTest {

    @Rule
    @JvmField
    var instantExecutor = InstantTaskExecutorRule()

    @Mock
    lateinit var liveDataProvider: CarDescriptionLiveDataProvider

    @Mock
    lateinit var observer: Observer<Resource<List<CarDescription>>>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    /**
     * Return car descriptions list
     */
    @Test
    fun getCarDescriptionsFromRepository() {
        val carDescription1 = CarDescription(
                id = "fake_id1",
                modelIdentifier = "fake_modelIdentifier",
                modelName = "fake_modelName",
                name = "fake_name",
                make = "fake_make",
                group = "fake_group",
                color = "fake_color",
                series = "fake_series",
                fuelType = CarDescription.FuelType.ELECTRIC,
                fuelLevel = 0.0,
                transmission = CarDescription.Transmission.AUTOMATIC,
                licensePlate = "fake_licensePlate",
                latitude = 0.0,
                longitude = 0.0,
                carImageUrl = "fake_url",
                innerCleanliness = CarDescription.Cleanliness.CLEAN)

        val carDescription2 = CarDescription(
                id = "fake_id2",
                modelIdentifier = "fake_modelIdentifier",
                modelName = "fake_modelName",
                name = "fake_name",
                make = "fake_make",
                group = "fake_group",
                color = "fake_color",
                series = "fake_series",
                fuelType = CarDescription.FuelType.PETROL,
                fuelLevel = 0.0,
                transmission = CarDescription.Transmission.AUTOMATIC,
                licensePlate = "fake_licensePlate",
                latitude = 0.0,
                longitude = 0.0,
                carImageUrl = "fake_url",
                innerCleanliness = CarDescription.Cleanliness.VERY_CLEAN)

        val carDescription3 = CarDescription(
                id = "fake_id3",
                modelIdentifier = "fake_modelIdentifier",
                modelName = "fake_modelName",
                name = "fake_name",
                make = "fake_make",
                group = "fake_group",
                color = "fake_color",
                series = "fake_series",
                fuelType = CarDescription.FuelType.DIESEL,
                fuelLevel = 0.0,
                transmission = CarDescription.Transmission.MANUAL,
                licensePlate = "fake_licensePlate",
                latitude = 0.0,
                longitude = 0.0,
                carImageUrl = "fake_url",
                innerCleanliness = CarDescription.Cleanliness.REGULAR)

        val carDescriptionsList = MutableLiveData<Resource<List<CarDescription>>>()
        val resource = Resource.success(listOf(carDescription1, carDescription2, carDescription3))
        carDescriptionsList.value = resource

        Mockito.`when`(liveDataProvider.getCarDescriptionList()).thenReturn(carDescriptionsList)
        val carDescriptionsMapViewModel = CarDescriptionsMapViewModel(liveDataProvider)

        carDescriptionsMapViewModel.carDescriptions.observeForever(observer)

        Mockito.verify(observer).onChanged(ArgumentMatchers.refEq(resource))

    }
}