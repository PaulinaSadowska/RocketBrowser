package com.nekodev.rocketbrowser.rockets

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.nekodev.rocketbrowser.R
import com.nekodev.rocketbrowser.RocketApplication
import com.nekodev.rocketbrowser.api.RocketService
import javax.inject.Inject

class RocketListActivity : AppCompatActivity() {

    @Inject
    lateinit var service: RocketService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rocket_list)

        injectDependencies()

        service.getRockets()
    }

    private fun injectDependencies() {
        (application as RocketApplication).component.inject(this)
    }
}
