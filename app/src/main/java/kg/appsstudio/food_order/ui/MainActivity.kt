package kg.appsstudio.food_order.ui

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerAppCompatActivity
import kg.appsstudio.food_order.R
import kg.appsstudio.food_order.databinding.ActivityMainBinding
import kg.appsstudio.food_order.extension.createFactory
import kg.appsstudio.food_order.repo.RepositoryImpl
import kg.appsstudio.food_order.room.entity.Food
import kg.appsstudio.food_order.ui.adapter.FoodAdapter
import kg.appsstudio.food_order.ui.adapter.OnClickListener
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    private lateinit var  vm  : MainViewModel

    private lateinit var  adapter: FoodAdapter

    private lateinit var  binding : ActivityMainBinding

    private var foodList : List<Food> = listOf()

    private var itemPosition : Int = 0

    @Inject
    lateinit var  repository : RepositoryImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        getAllList()
        setObserver()
        setRecyclerView()
        onClick()
    }

    private fun init(){
        Log.d(TAG, " >>> Initializing viewModel")

        val factory = MainViewModel(repository).createFactory()
        vm = ViewModelProvider(this,factory).get(MainViewModel::class.java)
    }

    private fun getAllList(){
        vm.getOrderList()
    }

    private fun setObserver(){
        vm.observableState.observe(this, Observer {
            when{
                it.success -> {
                    Handler().postDelayed({
                        binding.state = it
                    }, 700)

                    adapter.result = it.list!!
                    adapter.notifyItemChanged(itemPosition)
                    calculateTotalItem(it.list!!)
                }
                else -> binding.state = it
            }
        })
    }

    private fun calculateTotalItem(list : List<Food>){
        var totalItem = 0
        list.forEach {
            totalItem += it.quantity!!
        }
        binding.totalItem = totalItem.toString()
    }

    private fun setRecyclerView(){
        binding.rvFood.layoutManager = LinearLayoutManager(this)
        adapter = FoodAdapter(foodList, object : OnClickListener{
            override fun update(food: Food, position: Int) {
                itemPosition = position
                vm.updateItem(food)
            }
        })
        binding.rvFood.adapter = adapter
        binding.rvFood.itemAnimator = null
    }

    private fun onClick(){
        binding.viewBottom.setOnClickListener {

        }
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

    companion object {
        const val TAG = "MainActivity"
    }
}