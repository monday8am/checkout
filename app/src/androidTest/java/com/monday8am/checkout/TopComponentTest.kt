package com.monday8am.checkout

import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.monday8am.checkout.helpers.Result
import com.monday8am.checkout.redux.CheckoutState
import com.monday8am.checkout.redux.checkoutReducer
import com.monday8am.checkout.ui.TopComponent
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.rekotlin.Store

@RunWith(AndroidJUnit4::class)
class TopComponentTest {

    private lateinit var store: Store<CheckoutState>
    private lateinit var topComponent: TopComponent

    @Before
    fun setupStore() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        store = Store(::checkoutReducer, null)
        topComponent = TopComponent(RelativeLayout(appContext) as ViewGroup, store.dispatchFunction)
    }

    @Test
    fun testComponent() {
        val state = CheckoutState(15.0f, 11.0f, listOf(), listOf(), Result.Loading)

        topComponent.newState(state)

        Assert.assertEquals(topComponent.totalLabel.text, "15,00€")
        Assert.assertEquals(topComponent.discountLabel.text, "- 11,00€")
        Assert.assertEquals(topComponent.resultLabel.text, "4,00€")
    }
}