package com.monday8am.checkout

import android.app.Application
import android.content.Context
import com.monday8am.checkout.data.Repository
import com.monday8am.checkout.data.local.PreferencesHelper
import com.monday8am.checkout.data.local.ProductDao
import com.monday8am.checkout.data.local.ProductDatabase
import com.monday8am.checkout.data.remote.RemoteWebService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CheckoutApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        repository = Repository(
            photoDao = provideDataSource(applicationContext),
            preferences = providePreferences(applicationContext),
            webService = provideRemoteWebService(),
            scheduleProvider = AppSchedulerProvider()
        )
    }

    companion object {
        @get:Synchronized lateinit var instance: CheckoutApp
            private set

        var repository: Repository? = null
    }

    private fun providePreferences(context: Context): PreferencesHelper {
        return PreferencesHelper(context)
    }

    private fun provideDataSource(context: Context): ProductDao {
        val database = ProductDatabase.getInstance(context)
        return database.productDao()
    }

    private fun provideRemoteWebService(): RemoteWebService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.myjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit.create<RemoteWebService>(RemoteWebService::class.java)
    }
}
