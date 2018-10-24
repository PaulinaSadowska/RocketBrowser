package com.nekodev.rocketbrowser.rockets.details

import android.os.Bundle
import com.nekodev.rocketbrowser.api.RocketLaunch
import com.nekodev.rocketbrowser.api.RocketService
import com.nekodev.rocketbrowser.rockets.details.injection.RocketInitData
import com.nekodev.rocketbrowser.util.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class RocketDetailsPresenter @Inject constructor(private val service: RocketService,
                                                 private val rocketInitData: RocketInitData,
                                                 private val schedulerProvider: BaseSchedulerProvider)
    : RocketDetailsContract.Presenter {

    companion object {
        private const val KEY_DESCRIPTION = "details"
        private const val KEY_LAUNCHES = "launches"
    }

    private var view: RocketDetailsContract.View? = null
    private val disposable = CompositeDisposable()
    private var description: String? = null
    private var launches: List<RocketLaunch>? = null

    override fun onStateRestored(savedInstanceState: Bundle) {
        description = savedInstanceState.getString(KEY_DESCRIPTION)
        launches = savedInstanceState.getParcelableArrayList(KEY_LAUNCHES)
    }

    override fun subscribe(view: RocketDetailsContract.View) {
        this.view = view
        view.setToolbar(rocketInitData.rocketName)
        showOrFetchDescription()
        showOrFetchLaunches()
    }

    private fun showOrFetchDescription() {
        description?.let {
            onDescriptionFetched(it)
        } ?: run {
            fetchDescription()
        }
    }

    private fun fetchDescription() {
        disposable.add(service.getRocketDetails(rocketInitData.rocketId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeBy(
                        onSuccess = { onDescriptionFetched(it.description) },
                        onError = {
                            // do nothing
                        }
                ))
    }

    private fun showOrFetchLaunches() {
        launches?.let {
            onLaunchesFetched(it)
        } ?: run {
            fetchLaunches()
        }
    }

    private fun fetchLaunches() {
        view?.showProgress()
        disposable.add(service.getRocketLaunches(rocketInitData.rocketId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doFinally { view?.hideProgress() }
                .subscribeBy(
                        onSuccess = { onLaunchesFetched(it) },
                        onError = { onFetchLaunchesError() }
                ))
    }

    private fun onLaunchesFetched(launches: List<RocketLaunch>) {
        this.launches = launches
        val launchesAndYears = launches.groupBy { it.launchYear }
        view?.displayLaunches(launchesAndYears)
    }


    private fun onDescriptionFetched(description: String) {
        this.description = description
        view?.showDescription(description)
    }

    private fun onFetchLaunchesError() {
        view?.showError()
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        description?.let {
            savedInstanceState.putString(KEY_DESCRIPTION, it)
        }
        launches?.let {
            savedInstanceState.putParcelableArrayList(KEY_LAUNCHES, ArrayList(it))
        }
    }

    override fun unsubscribe() {
        view = null
        disposable.dispose()
    }

}