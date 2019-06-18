package com.monday8am.checkout.redux

import com.monday8am.checkout.data.applyDiscounts
import com.monday8am.checkout.data.totalPrice
import com.monday8am.checkout.helpers.Result
import com.monday8am.checkout.redux.CheckoutActions.*
import org.rekotlin.Action

fun checkoutReducer(action: Action, oldState: CheckoutState?): CheckoutState {
    var state = oldState ?: getInitialState()

    when (action) {
        is AddDiscounts -> {
            state = state.copy(
                discounts = action.payload,
                discountedPrice = state.selectedItems.applyDiscounts(action.payload)
                )
        }
        is AddProducts -> {
            state = state.copy(items = action.payload)
        }
        is ModifyCheckout -> {
            val mutable = state.selectedItems.toMutableList()

            if (action.remove) {
                val lastIndex = mutable.indexOfLast { it.code == action.product.code }
                if (lastIndex != -1) {
                    mutable.removeAt(lastIndex)
                }
            } else {
                mutable.add(action.product)
            }

            state = state.copy(
                selectedItems = mutable,
                discountedPrice = mutable.applyDiscounts(state.discounts),
                totalPrice = mutable.totalPrice()
                )
        }
    }

    return state
}

private fun getInitialState(): CheckoutState {
    return CheckoutState(
        totalPrice = 0f,
        discountedPrice = 0f,
        selectedItems = listOf(),
        discounts = listOf(),
        items = Result.Loading
        )
}
