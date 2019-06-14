package com.monday8am.checkout.reducer

import com.monday8am.checkout.*
import com.monday8am.checkout.data.Discount
import com.monday8am.checkout.data.DiscountType
import com.monday8am.checkout.data.Product
import com.monday8am.checkout.data.totalPrice
import com.monday8am.checkout.helpers.Result
import com.monday8am.checkout.helpers.asList
import com.monday8am.checkout.redux.CheckoutActions.*
import com.monday8am.checkout.redux.CheckoutState
import com.monday8am.checkout.redux.checkoutReducer
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.rekotlin.Store


class CheckoutReducerTest {

    private lateinit var store: Store<CheckoutState>

    @Before
    fun setupStore() {
        store = Store(
            reducer = ::checkoutReducer,
            state = null,
            middleware = listOf()
        )
    }

    @Test
    fun `check initial state`() {
        assertEquals(store.state.discounts, listOf<Discount>())
        assertEquals(store.state.selectedItems, listOf<Product>())
        assertEquals(store.state.items, Result.Loading)
        assertEquals(store.state.discountedPrice, 0f)
        assertEquals(store.state.totalPrice, 0f)
    }

    @Test
    fun `set product list from server`() {
        val action = AddProducts(payload = Result.ok(products))

        store.dispatch(action)

        assertEquals(store.state.items.asList(), products)
    }

    @Test
    fun `set discounts from server`() {
        val discount = Discount(discountType = DiscountType.TwoForOne, productCode = "MUG", newPrice = 10f)
        val discountAction = AddDiscounts(payload = listOf(discount))

        store.dispatch(discountAction)

        assertEquals(store.state.discounts, listOf(discount))
    }

    @Test
    fun `select for checkout and no discount`() {
        products.forEach {
            store.dispatch(ModifyCheckout(product = it))
        }

        assertEquals(store.state.selectedItems, products)
        assertEquals(store.state.discountedPrice, 0f)
        assertEquals(store.state.totalPrice, products.totalPrice())
    }

    @Test
    fun `select some products and delete two of them`() {
        products.forEach {
            store.dispatch(ModifyCheckout(product = it))
        }

        val mug = products[0]

        store.dispatch(ModifyCheckout(product = mug, remove = true))
        store.dispatch(ModifyCheckout(product = mug, remove = true))

        val totalMugs = products.count { it.code == mug.code }
        val selectedMugs = store.state.selectedItems.count { it.code == mug.code }

        assertEquals(selectedMugs, totalMugs - 2)
    }

    @Test
    fun `delete unselected product`() {
        store.dispatch(ModifyCheckout(product = products[0])) // Select a Mug
        store.dispatch(ModifyCheckout(product = products[1], remove = true)) // Try to delete a Voucher

        assertEquals(store.state.selectedItems.count(), 1)
        assertEquals(store.state.selectedItems[0], products[0])
        assertEquals(store.state.discountedPrice, 0f)
    }

    @Test
    fun `test use case number1`() {
        store.dispatch(AddDiscounts(payload = useCaseDiscounts))

        useCase1Products.forEach {
            store.dispatch(ModifyCheckout(product = it))
        }

        assertEquals(store.state.discountedPrice, 0f)
        assertEquals(store.state.totalPrice, 32.5f)
    }

    @Test
    fun `test use case number2`() {
        store.dispatch(AddDiscounts(payload = useCaseDiscounts))

        useCase2Products.forEach {
            store.dispatch(ModifyCheckout(product = it))
        }

        assertEquals(store.state.totalPrice - store.state.discountedPrice, 25f)
    }

    @Test
    fun `test use case number3`() {
        store.dispatch(AddDiscounts(payload = useCaseDiscounts))

        useCase3Products.forEach {
            store.dispatch(ModifyCheckout(product = it))
        }

        assertEquals(store.state.totalPrice - store.state.discountedPrice, 81f)
    }

    @Test
    fun `test use case number4`() {
        store.dispatch(AddDiscounts(payload = useCaseDiscounts))

        useCase4Products.forEach {
            store.dispatch(ModifyCheckout(product = it))
        }

        assertEquals(store.state.totalPrice - store.state.discountedPrice, 74.5f)
    }
}
