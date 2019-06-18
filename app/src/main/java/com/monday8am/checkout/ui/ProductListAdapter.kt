package com.monday8am.checkout.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.monday8am.checkout.R
import com.monday8am.checkout.redux.CheckoutActions
import kotlinx.android.synthetic.main.product_item.view.*
import org.rekotlin.DispatchFunction

class ProductListAdapter(
    private val dispatchFunction: DispatchFunction
) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    private var models: List<ProductInfo> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = models[position]

        with(holder) {
            nameTextView.text = item.product.name
            mPriceTextView.text = String.format(mView.context.getString(R.string.item_price), item.product.price)
            mCodeTextView.text = String.format(mView.context.getString(R.string.item_code), item.product.code)
            mCounterTextView.text = item.total.toString()

            removeButton.isEnabled = item.total > 0
            removeButton.alpha = if (item.total > 0) 1f else 0.3f
            mView.counterInfo.visibility = if (item.total == 0) View.GONE else View.VISIBLE

            mView.tag = item
            addButton.setOnClickListener {
                dispatchFunction(CheckoutActions.ModifyCheckout(product = item.product))
            }
            removeButton.setOnClickListener {
                dispatchFunction(CheckoutActions.ModifyCheckout(product = item.product, remove = true))
            }
        }
    }

    fun getItemList(): List<ProductInfo> = models

    fun setItemList(newModels: List<ProductInfo>) {
        models = newModels
    }

    override fun getItemCount(): Int = models.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val nameTextView: TextView = mView.nameTextView
        val mPriceTextView: TextView = mView.priceTextView
        val mCodeTextView: TextView = mView.codeTextView
        val mCounterTextView: TextView = mView.counterTextView
        val addButton: ImageButton = mView.addButton
        val removeButton: ImageButton = mView.removeButton
    }
}
