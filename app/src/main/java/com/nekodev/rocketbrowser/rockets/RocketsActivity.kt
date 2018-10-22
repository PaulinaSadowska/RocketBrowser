package com.nekodev.rocketbrowser.rockets

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.nekodev.rocketbrowser.R
import com.nekodev.rocketbrowser.RocketApplication
import javax.inject.Inject

class RocketsActivity : AppCompatActivity(), RocketsContract.View {

    @Inject
    lateinit var presenter: RocketsContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rocket_list)

        injectDependencies()

        savedInstanceState?.let {
            presenter.onStateRestored(savedInstanceState)
        }
        presenter.subscribe(this)
    }

    private fun injectDependencies() {
        (application as RocketApplication).component
                .getRocketsComponent()
                .inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unsubscribe()
    }
}
