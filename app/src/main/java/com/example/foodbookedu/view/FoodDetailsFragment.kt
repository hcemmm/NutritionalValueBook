package com.example.foodbookedu.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.foodbookedu.R
import com.example.foodbookedu.databinding.FragmentFoodDetailsBinding
import com.example.foodbookedu.util.downloadImage
import com.example.foodbookedu.util.makePlaceHolder
import com.example.foodbookedu.viewModel.FoodDetailsViewModel
import kotlinx.android.synthetic.main.fragment_food_details.*

class FoodDetailsFragment : Fragment() {

    lateinit var foodDetailVM : FoodDetailsViewModel
    lateinit var foodDetailName : TextView
    lateinit var foodDetailCalorie : TextView
    lateinit var foodDetailCarbs : TextView
    lateinit var foodDetailProtein : TextView
    lateinit var foodDetailFatContent : TextView

    var foodId = 0
    lateinit var dataBinding : FragmentFoodDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_food_details,container,false)
        val view = inflater.inflate(R.layout.fragment_food_details, container, false)

        foodDetailName = view.findViewById(R.id.foodDetailsName)
        foodDetailCalorie = view.findViewById(R.id.foodDetailsCalorie)
        foodDetailCarbs = view.findViewById(R.id.foodDetailsCarbs)
        foodDetailProtein = view.findViewById(R.id.foodDetailsProtein)
        foodDetailFatContent = view.findViewById(R.id.foodDetailsFoodFatContent)

        arguments?.let {
            foodId = FoodDetailsFragmentArgs.fromBundle(it).foodId
        }

        foodDetailVM = ViewModelProviders.of(this,).get(FoodDetailsViewModel::class.java)
        foodDetailVM.getRoomData(foodId)

        observeLiveData()

        return dataBinding.root
    }

    fun observeLiveData(){
        foodDetailVM.foodDetailLiveData.observe(viewLifecycleOwner, Observer { food ->
            food?.let {

                dataBinding.selectFood = it

               /* foodDetailName.text = it.name
                foodDetailCalorie.text = it.calorie
                foodDetailCarbs.text = it.carbohydrate
                foodDetailProtein.text = it.protein
                foodDetailFatContent.text = it.fatcontent
                context?.let {
                    foodDetailsImageView.downloadImage(food.image, makePlaceHolder(it))
                }

                */
            }
        })
    }
}