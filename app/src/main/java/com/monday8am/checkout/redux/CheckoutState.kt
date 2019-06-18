package com.monday8am.checkout.redux

import com.monday8am.checkout.data.Discount
import com.monday8am.checkout.data.Price
import com.monday8am.checkout.data.Product
import com.monday8am.checkout.helpers.Result
import org.rekotlin.StateType

data class CheckoutState(
    val totalPrice: Price,
    val discountedPrice: Price,
    val selectedItems: List<Product>,
    val discounts: List<Discount>,
    val items: Result<List<Product>, Throwable>
) : StateType
