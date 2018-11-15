package com.nekodev.rocketbrowser.rockets.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nekodev.rocketbrowser.R
import com.nekodev.rocketbrowser.api.Rocket
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_rocket.*
import java.util.*

class RocketsAdapter : RecyclerView.Adapter<RocketsAdapter.ViewHolder>() {

    private val rockets: MutableList<Rocket> = ArrayList()

    private val clickSubject = PublishSubject.create<Rocket>()

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rocket, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return rockets.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(rockets[position])
    }

    fun showRockets(rockets: List<Rocket>) {
        this.rockets.clear()
        this.rockets.addAll(rockets)
        notifyDataSetChanged()
    }

    val clickEvent: Observable<Rocket> = clickSubject

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(rocket: Rocket) {
            rocketName.text = rocket.name
            rocketCountry.text = rocket.country
            rocketEnginesCount.text = getEnginesLabel(rocket.engines.number)
            itemView.setOnClickListener {
                clickSubject.onNext(rocket)
            }
        }

        private fun getEnginesLabel(enginesCount: Int): String {
            return containerView.context.resources.getQuantityString(R.plurals.engines_count, enginesCount, enginesCount)
        }

    }
}