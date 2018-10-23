package com.nekodev.rocketbrowser.rockets.details

import android.os.Bundle
import com.nekodev.rocketbrowser.api.RocketDetails
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

    private var view: RocketDetailsContract.View? = null
    private val disposable = CompositeDisposable()

    override fun onStateRestored(savedInstanceState: Bundle) {
        //do nothing
    }

    override fun subscribe(view: RocketDetailsContract.View) {
        this.view = view
        view.setToolbar(rocketInitData.rocketName)
        fetchRocketDetails()
    }

    private fun fetchRocketDetails() {
        disposable.add(service.getRocketDetails(rocketInitData.rocketId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeBy(
                        onSuccess = { onDetailsFetched(it) },
                        onError = { onFetchDetailsError() }
                ))
    }

    private fun onDetailsFetched(rocketDetails: RocketDetails) {
        view?.showDescription(rocketDetails.description)
    }

    private fun onFetchDetailsError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        //do nothing
    }

    override fun unsubscribe() {
        view = null
        disposable.dispose()
    }

}