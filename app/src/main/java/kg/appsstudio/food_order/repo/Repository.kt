package kg.appsstudio.food_order.repo

import io.reactivex.Flowable
import io.reactivex.Single
import kg.appsstudio.food_order.room.database.AppDatabase
import kg.appsstudio.food_order.room.entity.Food

interface RepositoryImpl{
    fun update(food : Food): Single<Int>
    fun getCartItem():Flowable<List<Food>>
    fun getAllItem(): Flowable<List<Food>>
}

class Repository(private val db: AppDatabase): RepositoryImpl  {
    override fun update(food: Food): Single<Int> {
        return db.foodDao().updateFood(food)
    }

    override fun getCartItem(): Flowable<List<Food>> {
        return db.foodDao().getCartList()
    }

    override fun getAllItem(): Flowable<List<Food>> {
        return db.foodDao().getAllList()
    }
}