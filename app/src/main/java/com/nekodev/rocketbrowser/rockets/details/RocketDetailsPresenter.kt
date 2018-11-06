package com.nekodev.rocketbrowser.rockets.details

import android.os.Bundle
import com.nekodev.rocketbrowser.api.RocketDetails
import com.nekodev.rocketbrowser.api.RocketLaunch
import com.nekodev.rocketbrowser.api.RocketService
import com.nekodev.rocketbrowser.rockets.details.injection.RocketInitData
import com.nekodev.rocketbrowser.util.log
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class RocketDetailsPresenter @Inject constructor(private val service: RocketService,
                                                 private val rocketInitData: RocketInitData)
    : RocketDetailsContract.Presenter, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    companion object {
        private const val KEY_DESCRIPTION = "details"
        private const val KEY_LAUNCHES = "launches"
    }

    private var view: RocketDetailsContract.View? = null
    private var description: String? = null
    private var launches: List<RocketLaunch>? = null

    private lateinit var job: Job

    override fun onStateRestored(savedInstanceState: Bundle) {
        description = savedInstanceState.getString(KEY_DESCRIPTION)
        launches = savedInstanceState.getParcelableArrayList(KEY_LAUNCHES)
    }

    override fun subscribe(view: RocketDetailsContract.View) {
        this.view = view
        job = Job()
        view.setToolbar(rocketInitData.rocketName)
        showOrFetchDescription()
        showOrFetchLaunches()
    }

    private fun showOrFetchDescription() {
        description?.let {
            onDescriptionFetched(it)
        } ?: run {
            fetchDescription()
        }
    }

    private fun fetchDescription() {
        launch {
            try {
                val details = getDetails()
                withContext(Dispatchers.Main) {
                    onDescriptionFetched(details.description)
                }
            } catch (e: Exception) {
                //jobCancellationException
                log("error ${e.localizedMessage} ${e.javaClass.simpleName}")
            }
        }
    }


    private suspend fun getDetails(): RocketDetails {
        return service.getRocketDetails(rocketInitData.rocketId).await()
    }

    private fun showOrFetchLaunches() {
        launches?.let {
            processAndShowLaunches(it)
        } ?: run {
            fetchLaunches()
        }
    }

    private fun fetchLaunches() {
        view?.showProgress()
        launch {
            try {
                val fetchedLaunches = getRocketLaunches()
                withContext(Dispatchers.Main) {
                    processAndShowLaunches(fetchedLaunches)
                }
            } catch (e: Exception) {
                log("error :((( ${e.localizedMessage} ${e.javaClass.simpleName}")
                onFetchLaunchesError()
            }
        }

    }

    private fun processAndShowLaunches(fetchedLaunches: List<RocketLaunch>) {
        launches = fetchedLaunches
        val launchesAndYears = fetchedLaunches.groupBy { it.launchYear }
        view?.displayLaunches(launchesAndYears)
        view?.hideProgress()
    }

    private suspend fun getRocketLaunches() =
            service.getRocketLaunches(rocketInitData.rocketId).await()


    private fun onDescriptionFetched(description: String) {
        this.description = description
        view?.showDescription(description)
    }

    private fun onFetchLaunchesError() {
        view?.showError()
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        description?.let {
            savedInstanceState.putString(KEY_DESCRIPTION, it)
        }
        launches?.let {
            savedInstanceState.putParcelableArrayList(KEY_LAUNCHES, ArrayList(it))
        }
    }

    override fun unsubscribe() {
        view = null
        job.cancel()
    }

}