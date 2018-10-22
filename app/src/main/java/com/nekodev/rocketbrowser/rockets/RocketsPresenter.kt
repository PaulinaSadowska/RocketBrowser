package com.nekodev.rocketbrowser.rockets

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

    private var view: RocketsContract.View? = null
    private val disposable = CompositeDisposable()
    private var rockets: List<Rocket>? = null
    private var showOnlyActiveRockets = false

    override fun onStateRestored(savedInstanceState: Bundle) {
        //do nothing
    }

    override fun subscribe(view: RocketsContract.View) {
        this.view = view

        fetchAndShowRockets()
    }

    private fun fetchAndShowRockets() {
        rockets?.let {
            showRocketsBaseOnShowActiveFlag(it)
        } ?: run {
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
    }

    private fun onRocketsFetched(rockets: List<Rocket>) {
        this.rockets = rockets
        showRockets(rockets)
    }

    override fun onShowActiveRocketsCheckedChanged(checked: Boolean) {
        rockets?.let {
            this.showOnlyActiveRockets = checked
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

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        //do nothing
    }

    override fun unsubscribe() {
        view = null
    }

}