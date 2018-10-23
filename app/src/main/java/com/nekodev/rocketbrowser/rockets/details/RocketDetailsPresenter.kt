package com.nekodev.rocketbrowser.rockets.details

import android.os.Bundle
import com.nekodev.rocketbrowser.api.RocketService
import com.nekodev.rocketbrowser.rockets.details.injection.RocketInitData
import com.nekodev.rocketbrowser.util.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable
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
        //disposable.add(service.getRocketDetails(rocketInitData.rocketId))
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        //do nothing
    }

    override fun unsubscribe() {
        view = null
        disposable.dispose()
    }

}