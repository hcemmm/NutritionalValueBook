package com.example.foodbookedu.service

import com.example.foodbookedu.model.Food
import io.reactivex.Single
import retrofit2.http.GET

interface FoodAPI {

    @GET("hcemmm/JSONDataset/main/foods.json")
    fun getFood() :Single<List<Food>>

}