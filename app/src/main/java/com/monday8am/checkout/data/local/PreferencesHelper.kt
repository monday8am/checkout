package com.monday8am.checkout.data.local

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import com.google.gson.GsonBuilder
import com.monday8am.checkout.data.Product


class PreferencesHelper constructor(context: Context) {

    private val pref: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
    }

    private val gson = GsonBuilder().create()
    private val keySavedProducts = "saved_products"

    fun getSelectedProducts(): List<Product> {
        val jsonList = pref.getString(keySavedProducts, null) ?: return listOf()
        try {
            val products = gson.fromJson<Array<Product>>(jsonList, Array<Product>::class.java)
            return products.toList()
        } catch (ex: Exception) {
            Log.d("PreferencesHelper", "Error getting the saved products")
        }

        return listOf()
    }

    fun saveSelectedProducts(selected: List<Product>)  {
        val jsonList = gson.toJson(selected)
        pref.edit().putString(keySavedProducts, jsonList).apply()
    }
}
