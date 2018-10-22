package com.nekodev.rocketbrowser.rockets.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.nekodev.rocketbrowser.R
import kotlinx.android.synthetic.main.activity_rocket_details.*

class RocketDetailsActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_ROCKET_ID = "rocketId"

        fun createIntent(context: Context, rocketId: String): Intent {
            return Intent(context, RocketDetailsActivity::class.java).apply {
                putExtra(EXTRA_ROCKET_ID, rocketId)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rocket_details)
        val rocketId = intent.getStringExtra(EXTRA_ROCKET_ID)
        rocketId?.let {
            rocketIdText.text = it
        } ?: run {
            throw IllegalStateException("rocketId must be passed")
        }

    }
}