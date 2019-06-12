package com.monday8am.checkout.data

import com.monday8am.checkout.SchedulerProvider
import com.monday8am.checkout.data.local.PreferencesHelper
import com.monday8am.checkout.data.local.ProductDao
import com.monday8am.checkout.data.remote.RemoteWebService
import io.reactivex.Observable

class Repository(private val photoDao: ProductDao,
                 private val preferences: PreferencesHelper,
                 private val webService: RemoteWebService,
                 private val scheduleProvider: SchedulerProvider
) {

    fun updateProductList(): Observable<List<Product>> {
        return webService.listProducts()
            .flatMap {  products ->
                photoDao.insertProducts(products)
                    .toObservable()
                    .map { products }
            }
    }
}
