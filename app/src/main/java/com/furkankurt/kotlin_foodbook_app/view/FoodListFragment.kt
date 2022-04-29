package com.furkankurt.kotlin_foodbook_app.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.furkankurt.kotlin_foodbook_app.R
import com.furkankurt.kotlin_foodbook_app.adapter.FoodRecyclerAdapter
import com.furkankurt.kotlin_foodbook_app.viewModel.FoodListViewModel
import kotlinx.android.synthetic.main.fragment_food_list.*

class FoodListFragment : Fragment() {

    private lateinit var viewModel:FoodListViewModel
    private val recyclerviewFoodAdapter =FoodRecyclerAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProviders.of(this).get(FoodListViewModel::class.java)
        viewModel.refreshData()
        /*
         var foodString="Furkan"
                foodString.benimEklentim("Kurt")
         */


        foodListRecycler.layoutManager=LinearLayoutManager(context)
        foodListRecycler.adapter=recyclerviewFoodAdapter

        swipeRefreshLayout.setOnRefreshListener {
            foodLoadList.visibility=View.VISIBLE
            foodHataMesajıList.visibility=View.GONE
            foodListRecycler.visibility=View.GONE
            viewModel.refreshFromInternet()
            swipeRefreshLayout.isRefreshing=false
        }

        observeLiveData()


    }
    fun observeLiveData(){
        viewModel.foods.observe(viewLifecycleOwner, Observer {
            //Nullable yapıyoruz.
            it?.let {
                foodListRecycler.visibility=View.VISIBLE
                recyclerviewFoodAdapter.foodListesiGuncelle(it)

            }
        })
        //viewModelde booelan ayarladık.(foodErrorMessage)
        //HATA MESAJI VARSA GÖSTERİLECEK OLANI YAZ.YOKSA GÖSTERME
        viewModel.foodErrorMessage.observe(viewLifecycleOwner, Observer { hata->
            hata?.let {
                //Hata mesajı varsa
                if(it)
                {
                    foodHataMesajıList.visibility=View.VISIBLE
                    foodListRecycler.visibility=View.GONE
                }
                else{
                    foodHataMesajıList.visibility=View.GONE
                }

            }
        })
        viewModel.foodLoading.observe(viewLifecycleOwner, Observer { loading->
            loading?.let {
                //Yükleniyorsa
                if(it)
                {
                    foodListRecycler.visibility=View.GONE
                    foodHataMesajıList.visibility=View.GONE
                    foodLoadList.visibility=View.VISIBLE
                }
                else
                {
                    foodLoadList.visibility=View.GONE
                }
            }
        })
    }

}