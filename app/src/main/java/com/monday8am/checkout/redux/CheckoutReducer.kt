package com.monday8am.checkout.redux

import com.monday8am.checkout.data.Price
import com.monday8am.checkout.data.Product
import com.monday8am.checkout.data.discountMap
import com.monday8am.checkout.helpers.Result
import com.monday8am.checkout.redux.CheckoutAction.*
import org.rekotlin.Action


fun checkoutReducer(action: Action, oldState: CheckoutState?): CheckoutState {
    var state = oldState ?: getInitialState()

    when (action) {
        is AddDiscounts -> {
            state = state.copy(discounts = action.payload)
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

            val discount = state.discounts.fold(0f, { acc, nextDiscount ->
                discountMap[nextDiscount.discountType]?.let { discountLogic ->
                    return@fold acc + discountLogic.invoke(nextDiscount.productCode, mutable, nextDiscount.newPrice)
                }
                return@fold acc
            })

            state = state.copy(selectedItems = mutable, discountedPrice = discount, totalPrice = mutable.totalPrice())
        }
    }

    return state
}

private fun getInitialState(): CheckoutState {
    return CheckoutState(totalPrice = 0f,
                         discountedPrice = 0f,
                         selectedItems = listOf(),
                         discounts = listOf(),
                         items = Result.Loading)
}

fun List<Product>.totalPrice(): Price {
    var price = 0f
    forEach { price += it.price }
    return price
}
