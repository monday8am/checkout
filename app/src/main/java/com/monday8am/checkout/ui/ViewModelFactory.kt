package com.monday8am.checkout.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.monday8am.checkout.Injection
import com.monday8am.checkout.data.Repository
import com.monday8am.checkout.redux.checkoutReducer
import com.monday8am.checkout.redux.loggingMiddleware
import com.monday8am.checkout.redux.networkMiddlewareFactory
import io.reactivex.disposables.CompositeDisposable
import org.rekotlin.Store

class ViewModelFactory(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            val disposer = CompositeDisposable()
            when {
                isAssignableFrom(CheckoutViewModel::class.java) ->
                    CheckoutViewModel(Store(
                        reducer = ::checkoutReducer,
                        state = null,
                        middleware = listOf(loggingMiddleware, networkMiddlewareFactory(repository, disposer))),
                        disposer)
                else ->
                    throw IllegalArgumentException("Unknown CheckoutViewModel class: ${modelClass.name}")
            }
        } as T

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile private var INSTANCE: ViewModelFactory? = null

        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                INSTANCE ?: Injection.provideViewModelFactory(context = context)
                    .also { INSTANCE = it }
            }

        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
