package com.nekodev.rocketbrowser.rockets.details.adapter.launch

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.nekodev.rocketbrowser.R
import com.nekodev.rocketbrowser.api.RocketLaunch
import com.nekodev.rocketbrowser.rockets.details.adapter.ViewType
import com.nekodev.rocketbrowser.rockets.details.adapter.ViewTypeDelegateAdapter
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_rocket_launch.*

class RocketLaunchAdapter(private val dateFormat: LaunchDateFormat) : ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rocket_launch, parent, false)
        return LaunchViewHolder(view, dateFormat)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, viewType: ViewType) {
        if (viewType is RocketLauchItem && holder is LaunchViewHolder) {
            holder.bind(viewType.launch)
        }
    }

    class LaunchViewHolder(override val containerView: View,
                           private val dateFormat: LaunchDateFormat)
        : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(rocketLaunch: RocketLaunch) {
            missionNameText.text = rocketLaunch.missionName
            launchDateText.text = dateFormat.format(rocketLaunch.launchTimestamp)
            launchSuccessText.text = if (rocketLaunch.launchSuccess) "success" else "fail"
            Glide.with(containerView.context)
                    .load(rocketLaunch.links.missionPatch)
                    .into(launchPath)
        }
    }
}