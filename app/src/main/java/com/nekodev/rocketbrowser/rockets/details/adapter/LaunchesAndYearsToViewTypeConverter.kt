package com.nekodev.rocketbrowser.rockets.details.adapter

import com.nekodev.rocketbrowser.api.RocketLaunch
import com.nekodev.rocketbrowser.rockets.details.adapter.launch.RocketLauchItem
import com.nekodev.rocketbrowser.rockets.details.adapter.year.YearItem
import javax.inject.Inject

class LaunchesAndYearsToViewTypeConverter @Inject constructor() {

    fun convert(items: Map<String, List<RocketLaunch>>): List<ViewType> {
        return items.flatMap {
            mutableListOf<ViewType>(YearItem(it.key)).apply {
                addAll(it.value.map { RocketLauchItem(it) })
            }.toList()
        }
    }
}