package com.example.foodbookedu.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.foodbookedu.model.Food
import com.example.foodbookedu.service.FoodAPIService
import com.example.foodbookedu.service.FoodDataBase
import com.example.foodbookedu.util.PrivateSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FoodListViewModel(application: Application) : BaseViewModel(application) {
    val foods = MutableLiveData<List<Food>>()
    val foodErrorMessage = MutableLiveData<Boolean>()
    val foodLoading = MutableLiveData<Boolean>()
    var updateTime = 10 * 60 * 1000 * 1000 * 1000L


    private val foodAPIService = FoodAPIService()
    private val disposable = CompositeDisposable()
    private val privateSharedPreferences = PrivateSharedPreferences(getApplication())


    fun refleshData(){

        val saveTime = privateSharedPreferences.getTime()
        if (saveTime!= null && saveTime != 0L && System.nanoTime() - saveTime < updateTime){
            getDataFromSQL()
        }else{
            getDataFromInternet()
        }
    }

    fun refreshFromInternet(){
        getDataFromInternet()
    }

    fun getDataFromSQL(){
        launch {
            val foodList = FoodDataBase(getApplication()).foodDao().getAllFood()
            showFoods(foodList)
        }
    }

    fun getDataFromInternet(){

        foodLoading.value = true

        disposable.add(
            foodAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Food>>(){
                    override fun onSuccess(t: List<Food>) {
                        saveInSQL(t)
                        Toast.makeText(getApplication(),"Data Updated(Internet)",Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(e: Throwable) {
                        foodErrorMessage.value = true
                        foodLoading.value = false
                        e.printStackTrace()
                    }
                })
        )
    }

    fun showFoods(foodsList : List<Food>){
        foods.value = foodsList
        foodErrorMessage.value = false
        foodLoading.value = false
    }

    fun saveInSQL(foodList: List<Food>){

        launch {
            val dao = FoodDataBase(getApplication()).foodDao()
            dao.deleteAllFood()
            val uuidList = dao.insertAll(*foodList.toTypedArray())
            var i =0
            while (i<foodList.size){
                foodList[i].uuid = uuidList[i].toInt()
                i++
            }
            showFoods(foodList)
        }
        privateSharedPreferences.saveTime(System.nanoTime())
    }

}