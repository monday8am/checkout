package com.monday8am.checkout

import com.monday8am.checkout.data.twoForOneDiscountLogic
import org.junit.Assert.assertEquals
import org.junit.Test


class TwoForOneDiscountTests {

    @Test
    fun `twoForOne logic simple test`() {
        val discount = twoForOneDiscountLogic("VOUCHER", products)
        assertEquals(discount, 5f)
    }

    @Test
    fun `twoForOne against empty list`() {
        val discount = twoForOneDiscountLogic("VOUCHER", listOf())
        assertEquals(discount, 0f)
    }

    @Test
    fun `twoForOne against wrong product`() {
        val discount = twoForOneDiscountLogic("TEST", products)
        assertEquals(discount, 0f)
    }

    @Test
    fun `twoForOne with even number`() {
        val discount = twoForOneDiscountLogic("MUG", products)
        assertEquals(discount, 15f)
    }
}
