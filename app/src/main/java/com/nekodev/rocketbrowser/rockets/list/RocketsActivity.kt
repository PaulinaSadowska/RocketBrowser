package com.nekodev.rocketbrowser.rockets.list

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout
import com.jakewharton.rxbinding2.widget.RxCompoundButton
import com.nekodev.rocketbrowser.R
import com.nekodev.rocketbrowser.RocketApplication
import com.nekodev.rocketbrowser.api.Rocket
import com.nekodev.rocketbrowser.rockets.details.RocketDetailsActivity
import com.nekodev.rocketbrowser.util.ItemOffsetDecoration
import com.nekodev.rocketbrowser.util.hide
import com.nekodev.rocketbrowser.util.show
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_rocket_list.*
import javax.inject.Inject

class RocketsActivity : AppCompatActivity(), RocketsContract.View {

    @Inject
    lateinit var presenter: RocketsContract.Presenter

    private lateinit var adapter: RocketsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rocket_list)

        injectDependencies()
        adapter = RocketsAdapter()
        rocketsRecyclerView.adapter = adapter

        savedInstanceState?.let {
            presenter.onStateRestored(savedInstanceState)
        }
        presenter.subscribe(this)

        initializeViews()
    }

    private fun initializeViews() {
        rocketsRecyclerView.also {
            it.layoutManager = LinearLayoutManager(this)
            it.addItemDecoration(ItemOffsetDecoration(this, R.dimen.item_offset))
        }
    }

    override fun showActiveChecked(): Observable<Boolean> {
        return RxCompoundButton.checkedChanges(rocketsActiveSwitch)
    }

    override fun onRefresh(): Observable<Any> {
        return RxSwipeRefreshLayout.refreshes(rocketsSwipeToRefresh)
    }

    private fun injectDependencies() {
        (application as RocketApplication).component
                .getRocketsComponent()
                .inject(this)
    }

    override fun onRocketClicked(): Observable<Rocket> {
        return adapter.clickEvent
    }

    override fun showRockets(rockets: List<Rocket>) {
        adapter.showRockets(rockets)
    }

    override fun showProgress() {
        rocketsProgressBar.show()
        rocketsSwipeToRefresh.isEnabled = false
    }

    override fun hideProgress() {
        rocketsProgressBar.hide()
        rocketsSwipeToRefresh.isRefreshing = false
        rocketsSwipeToRefresh.isEnabled = true
    }

    override fun showError() {
        errorText.show()
    }

    override fun hideError() {
        errorText.hide()
    }

    override fun showWelcomeDialog(visitsCount: Int) {
        val welcomeMessage = getString(R.string.welcome_to_rocket_browser)
        AlertDialog.Builder(this)
                .setMessage("$welcomeMessage $visitsCount")
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    //do nothing
                }
                .create()
                .show()
    }

    override fun openRocketDetails(rocketId: String, rocketName: String) {
        startActivity(RocketDetailsActivity.createIntent(this, rocketId, rocketName))
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
