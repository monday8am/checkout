package com.monday8am.checkout.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

typealias Code = String

typealias Price = Float

@Entity(tableName = "products")
data class Product @JvmOverloads constructor(
    @PrimaryKey
    @ColumnInfo(name = "code") var code: Code,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "price") var price: Price = 0f
)

fun List<Product>.totalPrice(): Price {
    var price = 0f
    forEach { price += it.price }
    return price
}

fun List<Product>.applyDiscounts(discounts: List<Discount>): Price {
    return discounts.fold(0f, { acc, nextDiscount ->
        discountMap[nextDiscount.discountType]?.let { discountLogic ->
            return@fold acc + discountLogic.invoke(nextDiscount.productCode, this, nextDiscount.newPrice)
        }
        return@fold acc
    })
}
