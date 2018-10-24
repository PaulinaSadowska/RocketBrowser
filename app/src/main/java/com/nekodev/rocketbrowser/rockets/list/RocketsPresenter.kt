package com.nekodev.rocketbrowser.rockets.list

import android.os.Bundle
import com.nekodev.rocketbrowser.api.Rocket
import com.nekodev.rocketbrowser.api.RocketService
import com.nekodev.rocketbrowser.util.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class RocketsPresenter @Inject constructor(private val service: RocketService,
                                           private val schedulerProvider: BaseSchedulerProvider)
    : RocketsContract.Presenter {

    companion object {
        private const val KEY_FETCHED_ROCKETS = "fetchedRockets"
        private const val KEY_SHOW_ONLY_ACTIVE_ROCKETS = "showOnlyActive"
    }

    private var view: RocketsContract.View? = null
    private val disposable = CompositeDisposable()
    private var rockets: List<Rocket>? = null
    private var showOnlyActiveRockets = false
    private var firstLaunch = true

    override fun onStateRestored(savedInstanceState: Bundle) {
        rockets = savedInstanceState.getParcelableArrayList(KEY_FETCHED_ROCKETS)
        showOnlyActiveRockets = savedInstanceState.getBoolean(KEY_SHOW_ONLY_ACTIVE_ROCKETS)
        firstLaunch = false
    }

    override fun subscribe(view: RocketsContract.View) {
        this.view = view
        fetchAndShowRockets()

        if (firstLaunch) {
            view.showWelcomeDialog()
        }
    }

    private fun fetchAndShowRockets() {
        rockets?.let {
            showRocketsBaseOnShowActiveFlag(it)
        } ?: run {
            fetchRockets()
        }
    }

    private fun fetchRockets() {
        view?.showProgress()
        disposable.add(service.getRockets()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doFinally { view?.hideProgress() }
                .subscribeBy(
                        onSuccess = { onRocketsFetched(it) },
                        onError = { view?.showError() }
                ))
    }

    private fun onRocketsFetched(rockets: List<Rocket>) {
        this.rockets = rockets
        showRocketsBaseOnShowActiveFlag(rockets)
    }

    override fun onRefresh() {
        rockets = null
        view?.hideError()
        showRockets(emptyList())
        fetchAndShowRockets()
    }

    override fun onShowActiveRocketsCheckedChanged(checked: Boolean) {
        this.showOnlyActiveRockets = checked
        rockets?.let {
            showRocketsBaseOnShowActiveFlag(it)
        }
    }

    private fun showRocketsBaseOnShowActiveFlag(rockets: List<Rocket>) {
        if (showOnlyActiveRockets) {
            showRockets(rockets.filter { it.active })
        } else {
            showRockets(rockets)
        }
    }

    private fun showRockets(rockets: List<Rocket>) {
        view?.showRockets(rockets)
    }

    override fun onRocketClicked(rocket: Rocket) {
        view?.openRocketDetails(rocket.rocketId, rocket.name)
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        rockets?.let {
            savedInstanceState.putParcelableArrayList(KEY_FETCHED_ROCKETS, ArrayList(it))
        }
        savedInstanceState.putBoolean(KEY_SHOW_ONLY_ACTIVE_ROCKETS, showOnlyActiveRockets)
    }

    override fun unsubscribe() {
        view = null
        disposable.dispose()
    }

}