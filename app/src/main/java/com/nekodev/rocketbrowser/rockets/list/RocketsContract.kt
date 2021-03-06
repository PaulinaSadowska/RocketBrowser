package com.nekodev.rocketbrowser.rockets.list

import com.nekodev.rocketbrowser.BaseContract
import com.nekodev.rocketbrowser.api.Rocket

interface RocketsContract {
    interface View {
        fun showRockets(rockets: List<Rocket>)
        fun showError()
        fun hideError()
        fun showProgress()
        fun hideProgress()
        fun showWelcomeDialog()
        fun openRocketDetails(rocketId: String, rocketName: String)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun onShowActiveRocketsCheckedChanged(checked: Boolean)
        fun onRefresh()
        fun onRocketClicked(rocket: Rocket)
    }
}