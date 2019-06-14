package com.monday8am.checkout.helpers

import androidx.recyclerview.widget.DiffUtil
import com.monday8am.checkout.ui.ProductInfo

class DiffCallback(private val newItems: List<ProductInfo>,
                   private val oldItems: List<ProductInfo>): DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newItems.getOrNull(oldItemPosition)?.product == oldItems.getOrNull(oldItemPosition)?.product
    }

    override fun getOldListSize(): Int {
        return oldItems.count()
    }

    override fun getNewListSize(): Int {
        return newItems.count()
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newItems.getOrNull(oldItemPosition) == oldItems.getOrNull(newItemPosition)
    }
}