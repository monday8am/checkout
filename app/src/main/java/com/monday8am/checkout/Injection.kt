package com.monday8am.checkout

import android.content.Context
import com.monday8am.checkout.data.Repository
import com.monday8am.checkout.data.local.PreferencesHelper
import com.monday8am.checkout.data.local.ProductDao
import com.monday8am.checkout.data.local.ProductDatabase
import com.monday8am.checkout.data.remote.RemoteWebService
import com.monday8am.checkout.ui.ViewModelFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Enables injection of data sources.
 */
object Injection {

    private var repository: Repository? = null

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

        return retrofit.create(RemoteWebService::class.java)
    }

    private fun provideRepository(context: Context): Repository {
        return if (repository == null) {
            val repo = Repository(
                photoDao = provideDataSource(context),
                preferences = providePreferences(context),
                webService = provideRemoteWebService(),
                scheduleProvider = AppSchedulerProvider()
            )
            repository = repo
            repo
        } else {
            repository!!
        }
    }

    fun provideViewModelFactory(context: Context): ViewModelFactory {
        val repository = provideRepository(context)
        return ViewModelFactory(repository)
    }
}
