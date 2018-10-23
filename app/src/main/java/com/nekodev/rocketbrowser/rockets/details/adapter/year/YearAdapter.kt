package com.nekodev.rocketbrowser.rockets.details.adapter.year

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nekodev.rocketbrowser.R
import com.nekodev.rocketbrowser.rockets.details.adapter.ViewType
import com.nekodev.rocketbrowser.rockets.details.adapter.ViewTypeDelegateAdapter
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_year.*


class YearAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_year, parent, false)
        return YearViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, viewType: ViewType) {
        if (viewType is YearItem && holder is YearViewHolder) {
            holder.bind(viewType.year)
        }
    }

    class YearViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(year: String) {
            yearText.text = year
        }
    }
}