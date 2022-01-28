package com.example.foodbookedu.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit

class PrivateSharedPreferences {

    val TIME = "time"

    companion object{
        var sharedPreferences : SharedPreferences? = null


        @Volatile var instance : PrivateSharedPreferences? = null
        val lock = Any()
        operator fun invoke(context:Context) :PrivateSharedPreferences = instance?: synchronized(lock){
            instance ?: makePrivateSharedPreferences(context).also {
                instance = it
            }
        }

        fun makePrivateSharedPreferences(context:Context):PrivateSharedPreferences{
        sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return PrivateSharedPreferences()
        }
    }

    fun saveTime(time : Long){
        sharedPreferences?.edit(commit = true){
            putLong(TIME,time)
        }
    }
    fun getTime() = sharedPreferences?.getLong(TIME,0)

}