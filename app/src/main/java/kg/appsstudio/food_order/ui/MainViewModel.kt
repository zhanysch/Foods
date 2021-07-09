package kg.appsstudio.food_order.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kg.appsstudio.food_order.repo.RepositoryImpl
import kg.appsstudio.food_order.room.entity.Food

class MainViewModel(private val repository : RepositoryImpl): ViewModel() {

    var observableState: MutableLiveData<MainState> = MutableLiveData()

    private val compositeDisposable = CompositeDisposable()

    private var state = MainState()
    set(value) {
        field = value
        publishState(value)
    }

    fun updateItem(food : Food){
        compositeDisposable.add(
            repository.update(food)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    fun getOrderList(){
        Log.d(TAG, " >>> Received call to get order list")
        state = state.copy(loading = false,success = false,failure = false,list = null)
        compositeDisposable
    }

    fun gerCartList(){
        Log.d(TAG, " >>> Received call to get cart list")
        state = state.copy(loading = true,success = false,failure = false,list = null)
        compositeDisposable.add(
            repository.getCartItem()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    state =state.copy(loading = false,success = true,failure = false,list = it)
                },{
                    state = state.copy(
                        loading = false,
                        failure = true,
                        success = false,
                        message = it.localizedMessage
                    )
                })
        )
    }

    private fun publishState(state: MainState) {
        Log.d(TAG," >>> Publish State : $state")
        observableState.postValue(state)
    }
    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, " >>> Clearing compositeDisposable")
        compositeDisposable.clear()
    }
    companion object {
        private const val TAG = "MainViewModel"
    }
}