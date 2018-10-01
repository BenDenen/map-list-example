package com.bendenen.example.maplistexample.repository.datasource

import com.bendenen.example.maplistexample.models.CarDescription
import retrofit2.Call
import retrofit2.http.GET


interface WebService {
    @GET("cars.json")
    fun getCarDescriptionList(): Call<List<CarDescription>>
}