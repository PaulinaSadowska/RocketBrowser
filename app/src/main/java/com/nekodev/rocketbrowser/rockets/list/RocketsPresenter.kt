package com.nekodev.rocketbrowser.rockets.list

import android.os.Bundle
import com.nekodev.rocketbrowser.api.Rocket
import com.nekodev.rocketbrowser.api.RocketService
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class RocketsPresenter @Inject constructor(private val service: RocketService)
    : RocketsContract.Presenter, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    companion object {
        private const val KEY_FETCHED_ROCKETS = "fetchedRockets"
        private const val KEY_SHOW_ONLY_ACTIVE_ROCKETS = "showOnlyActive"
    }

    private lateinit var job: Job

    private var view: RocketsContract.View? = null
    private var rockets: List<Rocket>? = null
    private var showOnlyActiveRockets = false
    private var firstLaunch = true

    override fun onStateRestored(savedInstanceState: Bundle) {
        rockets = savedInstanceState.getParcelableArrayList(KEY_FETCHED_ROCKETS)
        showOnlyActiveRockets = savedInstanceState.getBoolean(KEY_SHOW_ONLY_ACTIVE_ROCKETS)
        firstLaunch = false
    }

    override fun subscribe(view: RocketsContract.View) {
        this.view = view
        job = Job()

        if (firstLaunch) {
            view.showWelcomeDialog()
        }

        fetchRocketsIfShouldAndShow()
    }

    private fun fetchRocketsIfShouldAndShow() {
        rockets?.let {
            showRocketsBaseOnShowActiveFlag(it)
        } ?: run {
            fetchAndShowRockets()
        }
    }

    private fun fetchAndShowRockets() {
        view?.showProgress()
        launch {
            try {
                val rockets = fetchRockets()
                withContext(Dispatchers.Main) {
                    showRocketsBaseOnShowActiveFlag(rockets)
                }
            } catch (e: Exception) {
                view?.showError()
            } finally {
                withContext(Dispatchers.Main) {
                    view?.hideProgress()
                }
            }
        }
    }

    private suspend fun fetchRockets() = service.getRockets().await()

    override fun onRefresh() {
        rockets = null
        view?.hideError()
        showRockets(emptyList())
        fetchRocketsIfShouldAndShow()
    }

    override fun onShowActiveRocketsCheckedChanged(checked: Boolean) {
        this.showOnlyActiveRockets = checked
        rockets?.let {
            showRocketsBaseOnShowActiveFlag(it)
        }
    }

    private fun showRocketsBaseOnShowActiveFlag(rockets: List<Rocket>) {
        this.rockets = rockets
        if (showOnlyActiveRockets) {
            showRockets(rockets.filter { it.active })
        } else {
            showRockets(rockets)
        }
    }

    private fun showRockets(rockets: List<Rocket>) {
        view?.showRockets(rockets)
    }

    override fun onRocketClicked(rocket: Rocket) {
        view?.openRocketDetails(rocket.rocketId, rocket.name)
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        rockets?.let {
            savedInstanceState.putParcelableArrayList(KEY_FETCHED_ROCKETS, ArrayList(it))
        }
        savedInstanceState.putBoolean(KEY_SHOW_ONLY_ACTIVE_ROCKETS, showOnlyActiveRockets)
    }

    override fun unsubscribe() {
        view = null
        job.cancel()
    }

}