package com.monday8am.checkout.ui

import androidx.lifecycle.ViewModel
import com.monday8am.checkout.redux.CheckoutState
import io.reactivex.disposables.CompositeDisposable
import org.rekotlin.StateType
import org.rekotlin.Store
import org.rekotlin.StoreSubscriber
import org.rekotlin.Subscription

class CheckoutViewModel(
    val store: Store<CheckoutState>,
    private val middlewareDisposer: CompositeDisposable
) : ViewModel() {

    private val componentSubscriptions = mutableListOf<StoreSubscriber<out StateType>>()

    override fun onCleared() {
        super.onCleared()
        middlewareDisposer.clear()
        componentSubscriptions.forEach { store.unsubscribe(it) }
    }

    fun <T : StateType> addAndSubscribeToStore(
        uiComponent: StoreSubscriber<T>,
        stateTransform: ((Subscription<CheckoutState>) -> Subscription<T>)?
    ) {
        componentSubscriptions.add(uiComponent)
        store.subscribe(uiComponent, stateTransform)
    }
}
