package com.nekodev.rocketbrowser.rockets.details

import com.nekodev.rocketbrowser.BaseContract
import com.nekodev.rocketbrowser.api.RocketLaunch

interface RocketDetailsContract {
    interface View {
        fun setToolbar(rocketName: String)
        fun showDescription(description: String)
        fun displayLaunches(launchesAndYears: Map<String, List<RocketLaunch>>)
    }

    interface Presenter : BaseContract.Presenter<View> {

    }
}