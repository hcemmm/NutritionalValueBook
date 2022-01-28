package com.example.foodbookedu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.foodbookedu.model.Food
import com.example.foodbookedu.R
import com.example.foodbookedu.databinding.FoodRecyclerItemBinding
import com.example.foodbookedu.util.downloadImage
import com.example.foodbookedu.util.makePlaceHolder
import com.example.foodbookedu.view.FoodListFragmentDirections
import kotlinx.android.synthetic.main.food_recycler_item.view.*

class FoodRecyclerAdapter(val foodList : ArrayList<Food>):RecyclerView.Adapter<FoodRecyclerAdapter.FoodViewHolder>(),FoodClickListener {
    class FoodViewHolder(var view :FoodRecyclerItemBinding) :RecyclerView.ViewHolder(view.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //val view = inflater.inflate(R.layout.food_recycler_item,parent,false)
        val view = DataBindingUtil.inflate<FoodRecyclerItemBinding>(inflater,R.layout.food_recycler_item,parent,false)
        return FoodViewHolder(view)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }
    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {

        holder.view.food = foodList[position]
        holder.view.listener = this

       /* holder.itemView.findViewById<TextView>(R.id.foodItemName).text = foodList.get(position).name
        holder.itemView.findViewById<TextView>(R.id.foodItemCalorie).text = foodList.get(position).calorie
        holder.itemView.findViewById<ImageView>(R.id.foodItemImageView).downloadImage(foodList.get(position).image,
            makePlaceHolder(holder.itemView.context))

        holder.itemView.setOnClickListener{
            val action = FoodListFragmentDirections.actionFoodListFragmentToFoodDetailsFragment(foodList.get(position).uuid)
            Navigation.findNavController(it).navigate(action)
        }*/

    }

    fun foodListUpdate(newFoodList:List<Food>){
        foodList.clear()
        foodList.addAll(newFoodList)
        notifyDataSetChanged()
    }

    override fun clickedFood(view: View) {
        val uuid = view.foodItemUuid.text.toString().toIntOrNull()
        uuid?.let {
            val action = FoodListFragmentDirections.actionFoodListFragmentToFoodDetailsFragment(it)
            Navigation.findNavController(view).navigate(action)
        }
    }
}