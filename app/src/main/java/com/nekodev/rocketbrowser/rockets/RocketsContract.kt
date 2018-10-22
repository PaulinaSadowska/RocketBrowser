package com.nekodev.rocketbrowser.rockets

import com.nekodev.rocketbrowser.BaseContract
import com.nekodev.rocketbrowser.api.Rocket

interface RocketsContract {
    interface View {
        fun showRockets(rockets: List<Rocket>)
        fun showError()
        fun showProgress()
        fun hideProgress()
        fun showWelcomeDialog()
    }

    interface Presenter : BaseContract.Presenter<RocketsContract.View> {
        fun onShowActiveRocketsCheckedChanged(checked: Boolean)
    }
}