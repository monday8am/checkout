package com.monday8am.checkout.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.monday8am.checkout.R
import com.monday8am.checkout.redux.CheckoutActions
import kotlinx.android.synthetic.main.product_item.view.*
import org.rekotlin.DispatchFunction


class ProductListAdapter(
    private val mValues: List<ProductInfo>,
    private val dispatchFunction: DispatchFunction) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mIdView.text = item.product.code
        holder.mContentView.text = "${item.product.name} (${item.total})"

        with(holder.mView) {
            tag = item
            addButton.setOnClickListener {
                dispatchFunction(CheckoutActions.ModifyCheckout(product = item.product))
            }
            removeButton.setOnClickListener {
                dispatchFunction(CheckoutActions.ModifyCheckout(product = item.product, remove = true))
            }
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.item_number
        val mContentView: TextView = mView.content
    }
}
