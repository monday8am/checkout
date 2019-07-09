package com.monday8am.checkout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.monday8am.checkout.ui.BottomComponent
import com.monday8am.checkout.ui.CheckoutViewModel
import com.monday8am.checkout.ui.TopComponent
import com.monday8am.checkout.ui.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel: CheckoutViewModel by lazy {
        ViewModelProviders.of(this, ViewModelFactory.getInstance(this)).get(CheckoutViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(viewModel) {
            addAndSubscribeToStore(TopComponent(topContainer, store.dispatchFunction)) {
                it.skipRepeats()
            }

            addAndSubscribeToStore(BottomComponent(bottomContainer, store.dispatchFunction)) {
                it.skipRepeats()
            }
        }
    }
}
