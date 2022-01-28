package com.example.foodbookedu.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodbookedu.model.Food
import com.example.foodbookedu.service.FoodDataBase
import kotlinx.coroutines.launch

class FoodDetailsViewModel(application: Application) : BaseViewModel(application) {

    val foodDetailLiveData = MutableLiveData<Food>()

    fun getRoomData(uuid:Int){
    launch {
        val dao = FoodDataBase(getApplication()).foodDao()
        val food = dao.getFood(uuid)
        foodDetailLiveData.value = food
    }


    }

}