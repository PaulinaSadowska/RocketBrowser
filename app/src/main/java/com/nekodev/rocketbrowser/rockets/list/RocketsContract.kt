package com.nekodev.rocketbrowser.rockets.list

import com.nekodev.rocketbrowser.BaseContract
import com.nekodev.rocketbrowser.api.Rocket
import io.reactivex.Observable

interface RocketsContract {
    interface View {
        fun showRockets(rockets: List<Rocket>)
        fun showError()
        fun hideError()
        fun showProgress()
        fun hideProgress()
        fun showWelcomeDialog(visitsCount: Int)
        fun openRocketDetails(rocketId: String, rocketName: String)

        fun showActiveChecked(): Observable<Boolean>
        fun onRefresh(): Observable<Any>
        fun onRocketClicked(): Observable<Rocket>
    }

    interface Presenter : BaseContract.Presenter<View>
}