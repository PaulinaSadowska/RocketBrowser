package com.nekodev.rocketbrowser.rockets.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.nekodev.rocketbrowser.R
import com.nekodev.rocketbrowser.RocketApplication
import com.nekodev.rocketbrowser.api.RocketLaunch
import com.nekodev.rocketbrowser.rockets.details.adapter.LaunchesAndYearsAdapter
import com.nekodev.rocketbrowser.rockets.details.injection.RocketInitData
import com.nekodev.rocketbrowser.util.ItemOffsetDecoration
import kotlinx.android.synthetic.main.activity_rocket_details.*
import javax.inject.Inject

class RocketDetailsActivity : AppCompatActivity(), RocketDetailsContract.View {

    @Inject
    lateinit var presenter: RocketDetailsContract.Presenter

    companion object {
        private const val EXTRA_ROCKET_ID = "rocketId"
        private const val EXTRA_ROCKET_NAME = "rocketName"

        fun createIntent(context: Context, rocketId: String, rocketName: String): Intent {
            return Intent(context, RocketDetailsActivity::class.java).apply {
                putExtra(EXTRA_ROCKET_ID, rocketId)
                putExtra(EXTRA_ROCKET_NAME, rocketName)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rocket_details)
        prepareAndInjectDependencies()

        savedInstanceState?.let {
            presenter.onStateRestored(savedInstanceState)
        }
        presenter.subscribe(this)

        launchesRecyclerView.let {
            it.layoutManager = LinearLayoutManager(this)
            it.addItemDecoration(ItemOffsetDecoration(this, R.dimen.launch_item_offset))
        }
    }

    private fun prepareAndInjectDependencies() {
        val rocketId = intent.getStringExtra(EXTRA_ROCKET_ID)
        val rocketName = intent.getStringExtra(EXTRA_ROCKET_NAME)
        if (rocketId != null && rocketName != null) {
            injectDependencies(rocketId, rocketName)
        } else {
            throw IllegalStateException("rocket id and name must be passed")
        }
    }

    private fun injectDependencies(rocketId: String, rocketName: String) {
        (application as RocketApplication).component
                .getRocketDetailsComponentBuilder()
                .rocketData(RocketInitData(rocketId, rocketName))
                .build()
                .inject(this)
    }

    override fun setToolbar(rocketName: String) {
        supportActionBar?.apply {
            title = rocketName
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }
    }

    override fun showDescription(description: String) {
        rocketDescriptionText.text = description
    }

    override fun displayLaunches(launchesAndYears: Map<String, List<RocketLaunch>>) {
        launchesRecyclerView.adapter = LaunchesAndYearsAdapter(launchesAndYears)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        presenter.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unsubscribe()
    }
}