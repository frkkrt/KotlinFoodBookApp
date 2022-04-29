package com.furkankurt.kotlin_foodbook_app.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.furkankurt.kotlin_foodbook_app.model.Food
import com.furkankurt.kotlin_foodbook_app.service.FoodAPIServis
import com.furkankurt.kotlin_foodbook_app.service.FoodDatabase
import com.furkankurt.kotlin_foodbook_app.util.SpecialSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FoodListViewModel(application: Application): BaseViewModel(application) {

    val foods = MutableLiveData<List<Food>>()
    val foodErrorMessage = MutableLiveData<Boolean>()
    val foodLoading = MutableLiveData<Boolean>()
    private var updateTime=10*60*1000*1000*1000L

    private val foodApiServis = FoodAPIServis()

    //1 kere kullan at.(Disposible)
    private val disposible = CompositeDisposable()
    private val specialSharedPreferences = SpecialSharedPreferences(getApplication())


    fun refreshData() {
        val saveTime=specialSharedPreferences.zamaniAl()
        //System.nanotime=güncel saat örneğin saat 4
        if(saveTime!=null&&saveTime!=0L &&System.nanoTime()-saveTime<updateTime)
        {
            //SqlLitedan çek
            dataSQLiteGet()
        }
        else
        {
            dataInternetGet()
        }


    }
    fun refreshFromInternet(){
        dataInternetGet()
    }

    private fun dataSQLiteGet(){
        foodLoading.value=true
        launch {
            val foodList=FoodDatabase(getApplication()).foodDao().getAllFood()
            foodView(foodList)
            Toast.makeText(getApplication(),"Besinleri Roomdan Aldık",Toast.LENGTH_LONG).show()
        }
    }

    private fun dataInternetGet() {
        //mainThread=Kullanıcının kullandığı ana tred
        //IO,Default,UI

        foodLoading.value = true
        disposible.add(
            foodApiServis.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Food>>() {
                    override fun onSuccess(t: List<Food>) {
                        sqLiteSakla(t)
                        Toast.makeText(getApplication(),"Besinleri İnternetten Aldık",Toast.LENGTH_LONG).show()

                    }

                    override fun onError(e: Throwable) {
                        foodErrorMessage.value = true
                        foodLoading.value = false
                        e.printStackTrace()
                    }

                })

        )

    }

    private fun foodView(foodList: List<Food>) {
        foods.value = foodList
        foodErrorMessage.value = false
        foodLoading.value = false
    }

    private fun sqLiteSakla(foodList: List<Food>) {
        launch {
            val dao = FoodDatabase(getApplication()).foodDao()
            dao.deleteAllFood()
            //* besinleri tek tek vermeye imkan verir.Listeyi tekil hale getirir.
            val uuidList = dao.insertAll(*foodList.toTypedArray())
            var i = 0
            while (i < uuidList.size){
                foodList[i].uuid = uuidList[i].toInt()
            i++
        }
        foodView(foodList)
    }

        specialSharedPreferences.zamaniKaydet(System.nanoTime())
}

}