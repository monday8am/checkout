package com.monday8am.checkout

import com.monday8am.checkout.data.Discount
import com.monday8am.checkout.data.DiscountType
import com.monday8am.checkout.data.Product

val products = listOf(
    Product(code = "MUG", name = "Coffee Mug", price = 7.5f),
    Product(code = "VOUCHER", name = "Voucher", price = 5f),
    Product(code = "TSHIRT", name = "T-Shirt", price = 20f),
    Product(code = "VOUCHER", name = "Voucher", price = 5f),
    Product(code = "MUG", name = "Coffee Mug", price = 7.5f),
    Product(code = "MUG", name = "Coffee Mug", price = 7.5f),
    Product(code = "MUG", name = "Coffee Mug", price = 7.5f),
    Product(code = "MUG", name = "Coffee Mug", price = 7.5f)
)

val useCaseDiscounts = listOf(
    Discount(discountType = DiscountType.TwoForOne, productCode = "VOUCHER", newPrice = 0f),
    Discount(discountType = DiscountType.Bulk, productCode = "TSHIRT", newPrice = 19f)
)

val useCase1Products = listOf(
    Product(code = "MUG", name = "Coffee Mug", price = 7.5f),
    Product(code = "VOUCHER", name = "Voucher", price = 5f),
    Product(code = "TSHIRT", name = "T-Shirt", price = 20f)
)

val useCase2Products = listOf(
    Product(code = "VOUCHER", name = "Voucher", price = 5f),
    Product(code = "TSHIRT", name = "T-Shirt", price = 20f),
    Product(code = "VOUCHER", name = "Voucher", price = 5f)
)

val useCase3Products = listOf(
    Product(code = "TSHIRT", name = "T-Shirt", price = 20f),
    Product(code = "TSHIRT", name = "T-Shirt", price = 20f),
    Product(code = "TSHIRT", name = "T-Shirt", price = 20f),
    Product(code = "VOUCHER", name = "Voucher", price = 5f),
    Product(code = "TSHIRT", name = "T-Shirt", price = 20f)
)

val useCase4Products = listOf(
    Product(code = "VOUCHER", name = "Voucher", price = 5f),
    Product(code = "TSHIRT", name = "T-Shirt", price = 20f),
    Product(code = "VOUCHER", name = "Voucher", price = 5f),
    Product(code = "VOUCHER", name = "Voucher", price = 5f),
    Product(code = "MUG", name = "Coffee Mug", price = 7.5f),
    Product(code = "TSHIRT", name = "T-Shirt", price = 20f),
    Product(code = "TSHIRT", name = "T-Shirt", price = 20f)
)
