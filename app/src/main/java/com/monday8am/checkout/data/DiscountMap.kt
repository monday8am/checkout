package com.monday8am.checkout.data

typealias DiscountLogic = (productCode: Code, products: List<Product>, newPrice: Price) -> Price

fun twoForOneDiscountLogic(productCode: Code, products: List<Product>, newPrice: Price = 0f): Price {
    val filtered = products.filter { it.code == productCode }
    if (filtered.isNotEmpty()) {
        return (filtered.count() / 2) * filtered[0].price
    }
    return 0f
}

fun bulkDiscountLogic(productCode: Code, products: List<Product>, newPrice: Price): Price {
    val filtered = products.filter { it.code == productCode }
    if (filtered.count() > 2) {
        return (filtered[0].price - newPrice) * filtered.count()
    }
    return 0f
}

val discountMap: HashMap<DiscountType, DiscountLogic> = hashMapOf(
    DiscountType.TwoForOne to ::twoForOneDiscountLogic,
    DiscountType.Bulk to ::bulkDiscountLogic
)
