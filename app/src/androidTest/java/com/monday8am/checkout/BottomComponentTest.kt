package com.monday8am.checkout

import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.view.isVisible
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.monday8am.checkout.data.Product
import com.monday8am.checkout.helpers.Result
import com.monday8am.checkout.redux.CheckoutState
import com.monday8am.checkout.redux.checkoutReducer
import com.monday8am.checkout.ui.BottomComponent
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.rekotlin.Store

@RunWith(AndroidJUnit4::class)
class BottomComponentTest {

    private lateinit var store: Store<CheckoutState>
    private lateinit var bottomComponent: BottomComponent

    private val products = listOf(
        Product(code = "VOUCHER", name = "Voucher", price = 5f),
        Product(code = "TSHIRT", name = "T-Shirt", price = 20f),
        Product(code = "VOUCHER", name = "Voucher", price = 5f)
    )
    @Before
    fun setupStore() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        store = Store(::checkoutReducer, null)
        bottomComponent = BottomComponent(RelativeLayout(appContext) as ViewGroup, store.dispatchFunction)
    }

    @Test
    fun testLoadingProducts() {
        val state = CheckoutState(0.0f, 0.0f, listOf(), listOf(), Result.Loading)

        bottomComponent.newState(state)

        Assert.assertEquals(bottomComponent.loaderView.isVisible, true)
        Assert.assertEquals(bottomComponent.recyclerView.isVisible, false)
        Assert.assertEquals(bottomComponent.errorTextView.isVisible, false)
    }

    @Test
    fun testProductsError() {
        val state = CheckoutState(0.0f, 0.0f, listOf(), listOf(), Result.err(Throwable(message = "error")))

        bottomComponent.newState(state)

        Assert.assertEquals(bottomComponent.loaderView.isVisible, false)
        Assert.assertEquals(bottomComponent.recyclerView.isVisible, false)
        Assert.assertEquals(bottomComponent.errorTextView.isVisible, true)
        Assert.assertEquals(bottomComponent.errorTextView.text, "error")
    }

    @Test
    fun testProductsSuccess() {
        val state = CheckoutState(0.0f, 0.0f, listOf(), listOf(), Result.ok(products))

        bottomComponent.newState(state)

        Assert.assertEquals(bottomComponent.loaderView.isVisible, false)
        Assert.assertEquals(bottomComponent.recyclerView.isVisible, true)
        Assert.assertEquals(bottomComponent.recyclerView.adapter?.itemCount, products.count())
        Assert.assertEquals(bottomComponent.errorTextView.isVisible, false)
    }
}
