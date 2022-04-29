package com.furkankurt.kotlin_foodbook_app.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.furkankurt.kotlin_foodbook_app.R
import com.furkankurt.kotlin_foodbook_app.databinding.FragmentFoodDetailBinding
import com.furkankurt.kotlin_foodbook_app.util.gorselIndir
import com.furkankurt.kotlin_foodbook_app.util.placeholderYap
import com.furkankurt.kotlin_foodbook_app.viewModel.FoodDetailViewModel
import kotlinx.android.synthetic.main.fragment_food_detail.*


class FoodDetailFragment : Fragment() {
    private lateinit var viewModel:FoodDetailViewModel
    private var foodId =0
    private lateinit var dataBinding:FragmentFoodDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_food_detail,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            foodId=FoodDetailFragmentArgs.fromBundle(it).foodId

        }

        viewModel= ViewModelProviders.of(this).get(FoodDetailViewModel::class.java)
        viewModel.roomGetData(foodId)



        observeLiveData()

    }
    fun observeLiveData(){
        viewModel.foodLiveData.observe(viewLifecycleOwner, Observer {food->
            food?.let {
                dataBinding.selectFood = it
                /*
                 foodNameDetail.text=it.foodName
                foodCalorieDetail.text=it.foodCalories
                foodCarbonhidratDetail.text=it.foodCarbonhidrat
                foodProteinDetail.text=it.foodProtein
                foodYagDetail.text=it.foodYag
                context?.let {it1->
                    foodImageDetail.gorselIndir(food.foodImage, placeholderYap(it1))
                }
                 */
            }
        })
    }


}