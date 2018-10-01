package com.bendenen.example.maplistexample.repository

import android.arch.lifecycle.LiveData
import android.util.Log
import com.bendenen.example.maplistexample.models.CarDescription
import com.bendenen.example.maplistexample.repository.database.CarDescriptionDao
import com.bendenen.example.maplistexample.repository.datasource.Resource
import com.bendenen.example.maplistexample.repository.datasource.WebService
import com.bendenen.example.maplistexample.utils.AppExecutors
import com.bendenen.example.maplistexample.utils.Utils
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class CarDescriptionLiveDataProvider @Inject constructor(val webService: WebService,
                                                              val carDescriptionDao: CarDescriptionDao,
                                                              val utils: Utils,
                                                              val appExecutors: AppExecutors) {

    val TAG = "CarDescriptionLiveData"

    open fun getCarDescriptionList(): LiveData<Resource<List<CarDescription>>> {
        return object : Repository<List<CarDescription>>(appExecutors) {

            override fun saveNetworkCallResult(data: List<CarDescription>?) {
                Log.d(TAG, "saveNetworkCallResult")
                data?.forEach { carDescriptionDao.insertCarDescription(it) }
            }

            override fun shouldLoadFromNetwork(data: List<CarDescription>?): Boolean {
                val shouldLoadFromNetwork = utils.hasConnection() && (data == null || data.isEmpty())
                Log.d(TAG, "shouldLoadFromNetwork: $shouldLoadFromNetwork")
                return shouldLoadFromNetwork
            }

            override fun loadFromDatabase(): LiveData<List<CarDescription>> {
                Log.d(TAG, "loadFromDatabase")
                return carDescriptionDao.queryCarDescriptionList()
            }

            override fun createNetworkCall(): Call<List<CarDescription>> {
                Log.d(TAG, "createNetworkCall")
                return webService.getCarDescriptionList()
            }
        }.asLiveData()
    }
}