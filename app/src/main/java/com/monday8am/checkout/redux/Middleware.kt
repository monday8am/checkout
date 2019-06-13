package com.monday8am.checkout.redux

import android.util.Log
import com.monday8am.checkout.CheckoutApp
import com.monday8am.checkout.helpers.Result
import io.reactivex.disposables.Disposable
import org.rekotlin.DispatchFunction
import org.rekotlin.Middleware
import org.rekotlin.ReKotlinInit

var productsDisp: Disposable? = null
var discountDisp: Disposable? = null

internal val loggingMiddleware: Middleware<CheckoutState> = { _, _ ->
    { next ->
        { action ->
            Log.d("NEW_ACTION:", Thread.currentThread().id.toString() + "-" + action.toString())
            next(action)
        }
    }
}

internal val networkMiddleware: Middleware<CheckoutState> = { dispatch, _ ->
    { next ->
        { action ->
            when (action) {
                is ReKotlinInit -> {
                    loadProducts(dispatch)
                    loadDiscounts(dispatch)
                }
            }
            next(action)
        }
    }
}

fun loadProducts(dispatch: DispatchFunction) {
    val repository = CheckoutApp.repository ?: return

    productsDisp?.dispose()
    productsDisp = repository.updateProductList()
        .subscribe ({ result ->
            dispatch(CheckoutActions.AddProducts(payload = Result.ok(result)))
        },{ error ->
            dispatch(CheckoutActions.AddProducts(payload = Result.err(error)))
        })
}

fun loadDiscounts(dispatch: DispatchFunction) {
    val repository = CheckoutApp.repository ?: return

    discountDisp?.dispose()
    discountDisp = repository.getDiscounts()
        .subscribe { result ->
            dispatch(CheckoutActions.AddDiscounts(payload = result))
        }
}
