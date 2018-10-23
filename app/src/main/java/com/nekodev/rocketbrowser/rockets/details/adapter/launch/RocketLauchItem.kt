package com.nekodev.rocketbrowser.rockets.details.adapter.launch

import com.nekodev.rocketbrowser.api.RocketLaunch
import com.nekodev.rocketbrowser.rockets.details.adapter.LAUNCH
import com.nekodev.rocketbrowser.rockets.details.adapter.ViewType

class RocketLauchItem(val launch: RocketLaunch) : ViewType{
    override val viewType = LAUNCH
}