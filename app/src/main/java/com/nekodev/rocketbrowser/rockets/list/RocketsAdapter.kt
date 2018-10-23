package com.nekodev.rocketbrowser.rockets.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nekodev.rocketbrowser.R
import com.nekodev.rocketbrowser.api.Rocket
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_rocket.*

class RocketsAdapter(private val rockets: List<Rocket>, private val itemClickListener: (rocketId: String) -> Unit) : RecyclerView.Adapter<RocketsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rocket, parent, false)
        return ViewHolder(view, itemClickListener)
    }

    override fun getItemCount(): Int {
        return rockets.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(rockets[position])
    }

    class ViewHolder(override val containerView: View, private val itemClickListener: (rocketId: String) -> Unit) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(rocket: Rocket) {
            rocketName.text = rocket.name
            rocketCountry.text = rocket.country
            rocketEnginesCount.text = getEnginesLabel(rocket.engines.number)
            containerView.setOnClickListener { itemClickListener.invoke(rocket.rocketId) }
        }

        private fun getEnginesLabel(enginesCount: Int): String {
            return containerView.context.resources.getQuantityString(R.plurals.engines_count, enginesCount, enginesCount)
        }

    }
}