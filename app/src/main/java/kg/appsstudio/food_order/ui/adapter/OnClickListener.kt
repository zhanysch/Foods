package kg.appsstudio.food_order.ui.adapter

import kg.appsstudio.food_order.room.entity.Food

interface OnClickListener {
    fun update(food : Food, position: Int)
}