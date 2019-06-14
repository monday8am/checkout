package com.monday8am.checkout.ui

import android.annotation.SuppressLint
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.monday8am.checkout.redux.checkoutReducer
import com.monday8am.checkout.redux.loggingMiddleware
import com.monday8am.checkout.redux.networkMiddleware
import org.rekotlin.Store

class ViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    private val middleware = listOf(networkMiddleware, loggingMiddleware)

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(CheckoutViewModel::class.java) ->
                    CheckoutViewModel(Store(
                        reducer = ::checkoutReducer,
                        state = null,
                        middleware = middleware))
                else ->
                    throw IllegalArgumentException("Unknown CheckoutViewModel class: ${modelClass.name}")
            }
        } as T

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile private var INSTANCE: ViewModelFactory? = null

        fun getInstance() =
            INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                INSTANCE ?: ViewModelFactory()
                    .also { INSTANCE = it }
            }

        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
