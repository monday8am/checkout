package com.monday8am.checkout.discounts

import com.monday8am.checkout.helpers.bulkDiscountLogic
import com.monday8am.checkout.products
import org.junit.Assert.assertEquals
import org.junit.Test


class BulkDiscountTest {

    @Test
    fun `bulk logic simple test`() {
        val discount = bulkDiscountLogic("MUG", products, 6f)
        assertEquals(discount, 7.5f)
    }

    @Test
    fun `bulk against empty list`() {
        val discount = bulkDiscountLogic("VOUCHER", listOf(), 10f)
        assertEquals(discount, 0f)
    }

    @Test
    fun `bulk against wrong product`() {
        val discount = bulkDiscountLogic("TEST", products, 10f)
        assertEquals(discount, 0f)
    }

    @Test
    fun `bulk test not enough products`() {
        val discount = bulkDiscountLogic("VOUCHER", products, 10f)
        assertEquals(discount, 0f)
    }
}
