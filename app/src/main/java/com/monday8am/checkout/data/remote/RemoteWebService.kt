package com.monday8am.checkout.data.remote

import com.monday8am.checkout.data.Discount
import com.monday8am.checkout.data.DiscountType
import com.monday8am.checkout.data.Product
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.http.GET
import java.util.concurrent.TimeUnit


interface RemoteWebService {
    @GET("/bins/4bwec/")
    fun listProducts(): Observable<List<Product>>
}

fun RemoteWebService.listDiscounts(): Observable<List<Discount>> {
    return Observable.just(listOf(
            Discount(discountType = DiscountType.Bulk, productCode = "TSHIRT", newPrice = 19f),
            Discount(discountType = DiscountType.TwoForOne, productCode = "VOUCHER", newPrice = 0f)
        )).delay(2, TimeUnit.SECONDS, Schedulers.computation())
}
