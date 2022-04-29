package com.furkankurt.kotlin_foodbook_app.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.furkankurt.kotlin_foodbook_app.model.Food
import com.furkankurt.kotlin_foodbook_app.service.FoodDatabase
import kotlinx.coroutines.launch

class FoodDetailViewModel(application: Application) :BaseViewModel(application) {

    //Seçilen Besini Göstermek için mutableLiveData
    val foodLiveData= MutableLiveData<Food>()

    fun roomGetData(uuid:Int){
        launch {
            val dao=FoodDatabase(getApplication()).foodDao()
            val food=dao.getFood(uuid)
            foodLiveData.value=food
        }

    }

}