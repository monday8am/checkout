package com.monday8am.checkout.redux

import com.monday8am.checkout.data.Discount
import com.monday8am.checkout.data.Price
import com.monday8am.checkout.data.Product
import org.rekotlin.StateType

data class AppState(val totalPrice: Price,
                    val discountedPrice: Price,
                    val selectedItems: List<Product>,
                    val discounts: List<Discount>,
                    val items: List<Product>) : StateType
