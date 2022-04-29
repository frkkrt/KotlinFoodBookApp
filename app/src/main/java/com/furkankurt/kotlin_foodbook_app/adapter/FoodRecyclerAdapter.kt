package com.furkankurt.kotlin_foodbook_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.furkankurt.kotlin_foodbook_app.R
import com.furkankurt.kotlin_foodbook_app.databinding.FoodRecyclerRowBinding
import com.furkankurt.kotlin_foodbook_app.model.Food
import com.furkankurt.kotlin_foodbook_app.view.FoodListFragmentDirections
import kotlinx.android.synthetic.main.food_recycler_row.view.*

class FoodRecyclerAdapter(private val foodList:ArrayList<Food>):RecyclerView.Adapter<FoodRecyclerAdapter.FoodViewHolder>(),FoodClickListener {
    class FoodViewHolder(var view: FoodRecyclerRowBinding):RecyclerView.ViewHolder(view.root)
    {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        //val view=inflater.inflate(R.layout.food_recycler_row,parent,false)
        val view=DataBindingUtil.inflate<FoodRecyclerRowBinding>(inflater,R.layout.food_recycler_row,parent,false)
        return FoodViewHolder(view)

    }

    override fun getItemCount(): Int {
        return foodList.size
    }
    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {

        holder.view.besin=foodList[position]
        holder.view.listener=this
        /*
         holder.itemView.foodName.text= foodList[position].foodName
        holder.itemView.foodCalories.text= foodList[position].foodCalories
        holder.itemView.setOnClickListener {
            val action=FoodListFragmentDirections.actionFoodListFragmentToFoodDetailFragment(
                foodList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }
        holder.itemView.imageView.gorselIndir(foodList[position].foodImage, placeholderYap(holder.itemView.context))
         */


    }

    fun foodListesiGuncelle(newFoodList:List<Food>)
    {
        foodList.clear()
        foodList.addAll(newFoodList)
        //veri değişlikliklerini kaydet
        notifyDataSetChanged()
    }

    override fun foodClick(view: View) {
        val uuid=view.foodUuid.text.toString().toIntOrNull()
        uuid?.let {
            val action= FoodListFragmentDirections.actionFoodListFragmentToFoodDetailFragment(it)
            Navigation.findNavController(view).navigate(action)
        }

    }


}