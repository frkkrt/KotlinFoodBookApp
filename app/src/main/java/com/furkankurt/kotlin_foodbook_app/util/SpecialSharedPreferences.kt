package com.furkankurt.kotlin_foodbook_app.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SpecialSharedPreferences {
    companion object {
        private val ZAMAN="zaman"
        private var sharedPreferences: SharedPreferences? = null

        @Volatile
        private var instance: SpecialSharedPreferences? = null
        private val lock = Any()
        operator fun invoke(context: Context): SpecialSharedPreferences = instance ?: synchronized(lock)
        {
            instance?:specialSharedPreferencesDo(context).also {
                instance=it
            }
        }
        private fun specialSharedPreferencesDo(context: Context):SpecialSharedPreferences{
            sharedPreferences=androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return SpecialSharedPreferences()
        }
    }

    fun zamaniKaydet(zaman:Long){
        sharedPreferences?.edit(commit=true) {
            putLong(ZAMAN,zaman)

        }
    }
    fun zamaniAl()= sharedPreferences?.getLong(ZAMAN,0)
}