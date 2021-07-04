package kg.appsstudio.food_order.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kg.appsstudio.food_order.R
import kg.appsstudio.food_order.databinding.FoodDataBinding
import kg.appsstudio.food_order.room.entity.Food

class FoodAdapter(var result : List<Food>,
                   private val listener: OnClickListener): RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
       return FoodViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.layout_single_item,parent,false),listener)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
       holder.bind(result[position],position)
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
      return  result.size
    }

    inner class FoodViewHolder(
        private val foodDataBinding: FoodDataBinding,
        private val listener: OnClickListener
    ) : RecyclerView.ViewHolder(foodDataBinding.root) {

        fun bind(food: Food, position: Int) {
            foodDataBinding.food = food
            if (food.quantity == 0) {
                foodDataBinding.shouldShowAddButton = true
                foodDataBinding.shouldPlusMinusButton = false
            } else {
                foodDataBinding.shouldShowAddButton = false
                foodDataBinding.shouldPlusMinusButton = false
            }
         onClick(food,position)
        }

        private fun onClick(food: Food, position: Int) {
            foodDataBinding.tvPlus.setOnClickListener {
                if (food.quantity == 20) return@setOnClickListener
                food.quantity = food.quantity!!.inc()
                listener.update(food, position)
            }
            foodDataBinding.tvMinus.setOnClickListener {
                food.quantity = food.quantity!!.dec()
                listener.update(food, position)
            }
            foodDataBinding.btnAdd.setOnClickListener {
                food.quantity = food.quantity!!.inc()
                listener.update(food, position)
            }
        }


    } }