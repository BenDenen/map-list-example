package com.bendenen.example.maplistexample

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.bendenen.example.maplistexample.models.CarDescription
import com.bendenen.example.maplistexample.repository.database.CarDescriptionDao
import com.bendenen.example.maplistexample.repository.database.Database
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class CarDescriptionDaoTest {
    @Rule
    @JvmField
    var instantExecutor = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<List<CarDescription>>

    private lateinit var database: Database
    private lateinit var carDescriptionDao: CarDescriptionDao

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        val context = InstrumentationRegistry.getTargetContext()
        database = Room.inMemoryDatabaseBuilder(context, Database::class.java).allowMainThreadQueries().build()
        carDescriptionDao = database.carDescriptionDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun queryCarDescriptionTest() {
        //given
        val carDescription = CarDescription(
                id = "fake_id",
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

        carDescriptionDao.insertCarDescription(carDescription)
        //when
        carDescriptionDao.queryCarDescriptionList().observeForever(observer)
        //then
        Mockito.verify(observer).onChanged(listOf(carDescription))
    }
}