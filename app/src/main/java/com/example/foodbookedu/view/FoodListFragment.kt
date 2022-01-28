package com.example.foodbookedu.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.foodbookedu.adapter.FoodRecyclerAdapter
import com.example.foodbookedu.R
import com.example.foodbookedu.viewModel.FoodListViewModel
import kotlinx.android.synthetic.main.fragment_food_list.*

class FoodListFragment : Fragment() {

    lateinit var foodListRecyclerView : RecyclerView
    lateinit var foodListLoading : ProgressBar
    lateinit var foodListErrorMessage: TextView
    lateinit var foodListSwipeRefleshLayout : SwipeRefreshLayout

    lateinit var foodListVM : FoodListViewModel
    val recyclerFoodAdapter = FoodRecyclerAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_food_list, container, false)

        foodListRecyclerView = view.findViewById(R.id.listRecylerView)
        foodListErrorMessage = view.findViewById(R.id.listErrorMessage)
        foodListSwipeRefleshLayout = view.findViewById(R.id.listSwipeRefleshLayout)
        foodListLoading = view.findViewById(R.id.listProgressBar)

        foodListSwipeRefleshLayout.setOnRefreshListener {
            foodListLoading.visibility = View.INVISIBLE
            foodListErrorMessage.visibility = View.INVISIBLE
            foodListRecyclerView.visibility = View.VISIBLE
            foodListVM.refreshFromInternet()
            foodListSwipeRefleshLayout.isRefreshing = false
        }


        foodListVM = ViewModelProviders.of(this).get(FoodListViewModel::class.java)
        foodListVM.refleshData()

        foodListRecyclerView.layoutManager = LinearLayoutManager(context)
        foodListRecyclerView.adapter = recyclerFoodAdapter

        observeLiveData()

        return view
    }

    fun observeLiveData(){

        foodListVM.foods.observe(viewLifecycleOwner, Observer {
            it?.let {
                foodListRecyclerView.visibility = View.VISIBLE
                recyclerFoodAdapter.foodListUpdate(it)
            }
        })

        foodListVM.foodErrorMessage.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it){
                    foodListErrorMessage.visibility = View.VISIBLE
                    foodListRecyclerView.visibility = View.INVISIBLE
                    foodListLoading.visibility = View.INVISIBLE
                }else{
                    foodListErrorMessage.visibility = View.INVISIBLE
                }

            }
        })
        foodListVM.foodLoading.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it){
                    foodListRecyclerView.visibility = View.INVISIBLE
                    foodListErrorMessage.visibility = View.INVISIBLE
                    foodListLoading.visibility = View.VISIBLE
                }else{
                    foodListLoading.visibility = View.INVISIBLE
                }
            }

        })

    }

}