package com.monday8am.checkout.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.VisibleForTesting
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.monday8am.checkout.R
import com.monday8am.checkout.data.Product
import com.monday8am.checkout.helpers.DiffCallback
import com.monday8am.checkout.helpers.Result
import com.monday8am.checkout.helpers.asList
import com.monday8am.checkout.redux.CheckoutState
import org.rekotlin.DispatchFunction
import org.rekotlin.StoreSubscriber

data class ProductInfo(val product: Product, val total: Int)

class BottomComponent(
    private val container: ViewGroup,
    private val dispatchFunction: DispatchFunction
) : StoreSubscriber<CheckoutState> {

    private val adapter = ProductListAdapter(dispatchFunction)

    private val root =
        LayoutInflater.from(container.context).inflate(
            R.layout.item_list_component_layout,
            container,
            true
        )

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val recyclerView: RecyclerView by lazy {
        val recycler = root.findViewById<RecyclerView>(R.id.productRecyclerView)
        recycler.adapter = adapter
        return@lazy recycler
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val loaderView: ContentLoadingProgressBar by lazy {
        root.findViewById<ContentLoadingProgressBar>(R.id.loaderView)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val errorTextView: TextView by lazy {
        root.findViewById<TextView>(R.id.errorTextView)
    }

    override fun newState(state: CheckoutState) {
        when (state.items) {
            Result.Loading -> {
                loaderView.show()
                errorTextView.visibility = View.GONE
                recyclerView.visibility = View.GONE
            }
            is Result.Err -> {
                loaderView.hide()
                errorTextView.visibility = View.VISIBLE
                errorTextView.text = state.items.error.message
                recyclerView.visibility = View.GONE
            }
            else -> {
                loaderView.hide()
                errorTextView.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE

                val newItems = state.items.asList().map {
                    ProductInfo(
                        product = it,
                        total = state.selectedItems.count { product -> product.code == it.code }
                    )
                }

                val diffResult = DiffUtil.calculateDiff(DiffCallback(newItems, adapter.getItemList()))
                adapter.setItemList(newItems)
                diffResult.dispatchUpdatesTo(adapter)
            }
        }
    }
}
