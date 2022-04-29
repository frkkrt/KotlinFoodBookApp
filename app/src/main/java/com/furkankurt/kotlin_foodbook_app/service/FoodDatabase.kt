package com.furkankurt.kotlin_foodbook_app.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.furkankurt.kotlin_foodbook_app.model.Food

@Database(entities = arrayOf(Food::class), version = 1)
abstract class FoodDatabase :RoomDatabase(){

    abstract fun foodDao():FoodDAO
    //Singleton=Farklı thredlerden tek bir objeye ulaşmak için kullanıyoruz.

    companion object {
        //@Volatile =diğer threadlere de görünüm yapmayı sağlıyor.
        @Volatile
        private var instances: FoodDatabase? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instances ?: synchronized(lock) {
            instances ?: databaseCreate(context).also {
                instances = it
            }
        }

        private fun databaseCreate(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            FoodDatabase::class.java, "foodDatabase"
        ).build()


    }
}