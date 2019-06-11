package com.monday8am.checkout.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.monday8am.checkout.R
import com.monday8am.checkout.redux.CheckoutState
import org.rekotlin.StoreSubscriber

class TopComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0): RelativeLayout(context, attrs, defStyle, defStyleRes), StoreSubscriber<CheckoutState> {

    init {
        LayoutInflater.from(context).inflate(R.layout.top_component_layout, this)
    }

    override fun newState(state: CheckoutState) {

    }
}
