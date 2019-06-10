package com.monday8am.checkout.redux

import com.monday8am.checkout.data.Discount
import com.monday8am.checkout.data.Product
import com.monday8am.checkout.helpers.Result
import org.rekotlin.Action

sealed class CheckoutAction : Action {

    data class AddProducts(val payload: Result<List<Product>, Throwable>) : CheckoutAction()

    data class AddDiscounts(val payload: List<Discount>) : CheckoutAction()

    data class ModifyCheckout(val product: Product, val remove: Boolean = false) : CheckoutAction()

}
