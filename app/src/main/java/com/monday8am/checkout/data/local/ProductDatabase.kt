package com.monday8am.checkout.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.monday8am.checkout.data.Product

@Database(entities = [Product::class], version = 1)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object {

        private var instance: ProductDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): ProductDatabase {
            synchronized(lock) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        ProductDatabase::class.java, "Product.db")
                        .build()
                }
                return instance!!
            }
        }
    }
}
