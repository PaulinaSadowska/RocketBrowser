package com.nekodev.rocketbrowser.rockets.details

import com.nekodev.rocketbrowser.BaseContract

interface RocketDetailsContract {
    interface View {
        fun setToolbar(rocketName: String)
    }

    interface Presenter : BaseContract.Presenter<View> {

    }
}