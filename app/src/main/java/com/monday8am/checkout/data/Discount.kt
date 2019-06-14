package com.monday8am.checkout.data


enum class DiscountType {
    TwoForOne,
    Bulk
}

data class Discount(val discountType: DiscountType,
                    val productCode: Code,
                    val newPrice: Price?)
