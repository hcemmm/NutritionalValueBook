package com.example.foodbookedu.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.foodbookedu.model.Food

@Database(entities = [Food::class], version = 1, exportSchema = false)
abstract class FoodDataBase : RoomDatabase() {

    abstract fun foodDao() : FoodDAO

    companion object {

        @Volatile var instance : FoodDataBase? = null

        val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: createDataBase(context).also {
                instance = it
            }
        }
        private fun createDataBase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            FoodDataBase::class.java,"fooddata").build()
    }
}