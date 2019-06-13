package com.monday8am.checkout.data.local

import androidx.room.*
import com.monday8am.checkout.data.Product
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface ProductDao {

    @Query("SELECT * FROM Products")
    fun getProducts(): Flowable<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProducts(products: List<Product>): Single<List<Long>>

    @Update
    fun updateProduct(product: Product): Completable

}
