package com.monday8am.checkout.data

typealias Code = String

typealias Price = Float

data class Product(val code: Code,
                   val name: String,
                   val price: Price)
