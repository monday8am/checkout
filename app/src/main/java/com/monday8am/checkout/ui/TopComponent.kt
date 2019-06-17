package com.monday8am.checkout.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.VisibleForTesting
import com.monday8am.checkout.R
import com.monday8am.checkout.redux.CheckoutState
import org.rekotlin.DispatchFunction
import org.rekotlin.StoreSubscriber

class TopComponent(
    private val container: ViewGroup,
    private val dispatchFunction: DispatchFunction): StoreSubscriber<CheckoutState> {

    private val root =
        LayoutInflater.from(container.context).inflate(
            R.layout.top_component_layout,
            container,
            true
        )

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val resultLabel: TextView by lazy {
        root.findViewById<TextView>(R.id.resultValueTextView)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val totalLabel: TextView by lazy {
        root.findViewById<TextView>(R.id.totalValueTextView)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val discountLabel: TextView by lazy {
        root.findViewById<TextView>(R.id.discountValueTextView)
    }

    override fun newState(state: CheckoutState) {
        val format = container.context.getString(R.string.price)

        resultLabel.text = String.format(format, state.totalPrice - state.discountedPrice)
        totalLabel.text = String.format(format, state.totalPrice)
        val lessStr = if (state.discountedPrice > 0) "- " else ""
        discountLabel.text = "${lessStr}${String.format(format, state.discountedPrice)}"
    }
}
