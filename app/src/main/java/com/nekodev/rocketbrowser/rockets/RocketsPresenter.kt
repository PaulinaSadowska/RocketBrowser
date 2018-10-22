package com.nekodev.rocketbrowser.rockets

import android.os.Bundle
import com.nekodev.rocketbrowser.api.RocketService
import javax.inject.Inject

class RocketsPresenter @Inject constructor(val service: RocketService) : RocketsContract.Presenter {

    private var view: RocketsContract.View? = null

    override fun onStateRestored(savedInstanceState: Bundle) {
        //do nothing
    }

    override fun subscribe(view: RocketsContract.View) {
        this.view = view
        service.getRockets()//todo!!
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        //do nothing
    }

    override fun unsubscribe() {
        view = null
    }

}