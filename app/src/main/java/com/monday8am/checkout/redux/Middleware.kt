package com.monday8am.checkout.redux

import android.util.Log
import com.monday8am.checkout.data.Repository
import com.monday8am.checkout.helpers.Result
import io.reactivex.disposables.CompositeDisposable
import org.rekotlin.Middleware
import org.rekotlin.ReKotlinInit

internal val loggingMiddleware: Middleware<CheckoutState> = { _, _ ->
    { next ->
        { action ->
            Log.d("NEW_ACTION:", Thread.currentThread().id.toString() + "-" + action.toString())
            next(action)
        }
    }
}

fun networkMiddlewareFactory(repository: Repository, disposer: CompositeDisposable): Middleware<CheckoutState> {
    return { dispatch, _ ->
        { next ->
            { action ->
                when (action) {
                    is ReKotlinInit -> {
                        val initDisp = repository.getProducts()
                            .doOnNext { dispatch(CheckoutActions.AddProducts(payload = Result.ok(it))) }
                            .doOnError { dispatch(CheckoutActions.AddProducts(payload = Result.err(it))) }
                            .flatMap { repository.getDiscounts() }
                            .subscribe { result ->
                                dispatch(CheckoutActions.AddDiscounts(payload = result))
                            }
                        disposer.add(initDisp)
                    }
                }
                next(action)
            }
        }
    }
}
