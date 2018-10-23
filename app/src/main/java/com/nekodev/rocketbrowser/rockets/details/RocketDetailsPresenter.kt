package com.nekodev.rocketbrowser.rockets.details

import android.os.Bundle
import com.nekodev.rocketbrowser.api.RocketService
import com.nekodev.rocketbrowser.util.BaseSchedulerProvider
import javax.inject.Inject

class RocketDetailsPresenter @Inject constructor(private val service: RocketService,
                                                 private val schedulerProvider: BaseSchedulerProvider)
    : RocketDetailsContract.Presenter {

    override fun onStateRestored(savedInstanceState: Bundle) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun subscribe(view: RocketDetailsContract.View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unsubscribe() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}