package kg.appsstudio.food_order.ui.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerAppCompatActivity
import kg.appsstudio.food_order.R
import kg.appsstudio.food_order.databinding.ActivityCartBinding
import kg.appsstudio.food_order.extension.createFactory
import kg.appsstudio.food_order.repo.RepositoryImpl
import kg.appsstudio.food_order.room.entity.Food
import kg.appsstudio.food_order.ui.MainViewModel
import kg.appsstudio.food_order.ui.adapter.FoodAdapter
import kg.appsstudio.food_order.ui.adapter.OnClickListener
import javax.inject.Inject

class CartActivity : DaggerAppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private lateinit var adapter: FoodAdapter

    private lateinit var binding: ActivityCartBinding

    private var cartList: List<Food> = listOf()

    private var itemPosition: Int = 0

    @Inject
    lateinit var orderRepoI:RepositoryImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)

        init()
        getCartList()
        setObserver()
        setRecyclerView()
        onClicks()

    }

    private fun init() {
        Log.d(TAG, " >>> Initializing viewModel")

        val factory = MainViewModel(orderRepoI).createFactory()
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
    }

    private fun getCartList() {
        viewModel.gerCartList()
    }

    private fun setObserver() {
        viewModel.observableState.observe(this, Observer {
            when {
                it.success -> {
                    Handler().postDelayed({
                        binding.state = it
                    }, 700)

                    cartList = it.list!!
                    if (cartList.size > 2) {
                        adapter.result = listOf(cartList[0], cartList[1])
                        binding.shouldShowMore = true
                    } else {
                        adapter.result = cartList
                        binding.shouldShowMore = false
                    }
                    adapter.notifyItemChanged(itemPosition)
                    calculateTotalPrice(cartList)
                }
                else -> binding.state = it
            }
        })
    }

    private fun calculateTotalPrice(cartList: List<Food>?) {
        var totalPrice = 0
        cartList?.let { list ->
            list.forEach {
                if (it.quantity!! != 0) {
                    totalPrice += it.rate.toInt() * it.quantity!!
                }
            }
        }
        binding.totalPrice = String.format(getString(R.string.total_price), totalPrice.toString())
    }

    private fun setRecyclerView() {
        binding.rvCart.layoutManager = LinearLayoutManager(this)
        adapter = FoodAdapter(cartList, object : OnClickListener {
            override fun update(food: Food, position: Int) {
                itemPosition = position
                viewModel.updateItem(food)
            }
        })
       binding.rvCart.adapter = adapter
        binding.rvCart.itemAnimator = null
    }

    private fun onClicks() {
        binding.viewPlaceOrderBottom.setOnClickListener {  }
        binding.tvShowMore.setOnClickListener {
            binding.shouldShowMore = false
            adapter.result = cartList
            adapter.notifyDataSetChanged()
        }
        binding.btnBack.setOnClickListener { onBackPressed() }
    }

    companion object {
        const val TAG = "CartActivity"

        fun start(context: Context) {
            context.startActivity(Intent(context, CartActivity::class.java))
        }
    }
}
