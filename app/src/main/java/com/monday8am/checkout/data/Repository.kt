package com.monday8am.checkout.data

import com.monday8am.checkout.SchedulerProvider
import com.monday8am.checkout.data.local.PreferencesHelper
import com.monday8am.checkout.data.local.ProductDao
import com.monday8am.checkout.data.remote.RemoteWebService
import com.monday8am.checkout.data.remote.listDiscounts
import io.reactivex.Observable

class Repository(
    private val photoDao: ProductDao,
    private val preferences: PreferencesHelper,
    private val webService: RemoteWebService,
    private val scheduleProvider: SchedulerProvider
) {

    fun getProducts(): Observable<List<Product>> {
        return photoDao.getProducts()
            .subscribeOn(scheduleProvider.io())
            .toObservable()
            .flatMap {
                return@flatMap if (it.isNotEmpty()) {
                    Observable.just(it)
                } else {
                    webService
                        .listProducts()
                        .subscribeOn(scheduleProvider.io())
                        .flatMap { dto ->
                            photoDao.insertProducts(dto.products)
                                .subscribeOn(scheduleProvider.io())
                                .toObservable()
                                .map { dto.products }
                        }
                }
            }
            .observeOn(scheduleProvider.ui())
    }

    fun getDiscounts(): Observable<List<Discount>> {
        return webService.listDiscounts()
            .subscribeOn(scheduleProvider.io())
            .observeOn(scheduleProvider.ui())
    }
}
