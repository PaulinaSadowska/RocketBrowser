package com.nekodev.rocketbrowser.rockets

import com.nekodev.rocketbrowser.BaseContract

interface RocketsContract {
    interface View

    interface Presenter : BaseContract.Presenter<RocketsContract.View>
}