package com.furkankurt.kotlin_foodbook_app.service

import com.furkankurt.kotlin_foodbook_app.model.Food
import io.reactivex.Single
import retrofit2.http.GET

interface FoodAPI {
    //GET,POST
    //https://raw.githubusercontent.com/atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json
    //BASE_URL
    @GET("atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json")
    //Single(RXJava)
    //Call(Retrofit)
    fun getFood(): Single<List<Food>>


}