package com.furkankurt.kotlin_foodbook_app.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.furkankurt.kotlin_foodbook_app.model.Food

@Dao
interface FoodDAO {

    //Data Access Object
    //suspend->Coroutines
    //List<Long>->Idler long
    //vararg=Aldığımız argüment 1 den fazla olabilir demektir.

    @Insert
    suspend fun insertAll(vararg food:Food) :List<Long>
    @Query("SELECT * FROM Food")
    suspend fun getAllFood():List<Food>

    @Query("SELECT * FROM Food WHERE uuid=:foodId " )
    suspend fun getFood(foodId:Int):Food

    @Query("DELETE FROM food")
    suspend fun deleteAllFood()

}