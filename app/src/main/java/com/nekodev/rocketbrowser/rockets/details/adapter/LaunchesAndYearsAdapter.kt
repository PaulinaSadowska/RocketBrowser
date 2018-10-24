package com.nekodev.rocketbrowser.rockets.details.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.nekodev.rocketbrowser.api.RocketLaunch
import com.nekodev.rocketbrowser.rockets.details.adapter.launch.LaunchDateFormat
import com.nekodev.rocketbrowser.rockets.details.adapter.launch.RocketLaunchAdapter
import com.nekodev.rocketbrowser.rockets.details.adapter.year.YearAdapter


class LaunchesAndYearsAdapter(launchesAndYears: Map<String, List<RocketLaunch>>,
                              dateFormat: LaunchDateFormat,
                              converter: LaunchesAndYearsToViewTypeConverter)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val delegateAdapters: Map<Int, ViewTypeDelegateAdapter> = mapOf(
            LAUNCH to RocketLaunchAdapter(dateFormat),
            YEAR to YearAdapter()
    )

    private val items = converter.convert(launchesAndYears)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapters[viewType]?.onCreateViewHolder(parent)
                ?: throw IllegalStateException("no adapter for the given type")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters[getItemViewType(position)]?.onBindViewHolder(holder, items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return this.items[position].viewType
    }
}