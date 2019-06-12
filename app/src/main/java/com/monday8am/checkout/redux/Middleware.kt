package com.monday8am.checkout.redux

import android.util.Log
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

internal val networkMiddleware: Middleware<CheckoutState> = { dispatch, getState ->
    { next ->
        { action ->
            when (action) {
                is ReKotlinInit -> {

                }
                is CheckoutActions.AddProducts -> {

                }
            }
            next(action)
        }
    }
}