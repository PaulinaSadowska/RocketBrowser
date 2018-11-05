package com.nekodev.rocketbrowser.rockets.details

import android.os.Bundle
import android.util.Log
import com.nekodev.rocketbrowser.api.RocketDetails
import com.nekodev.rocketbrowser.api.RocketLaunch
import com.nekodev.rocketbrowser.api.RocketService
import com.nekodev.rocketbrowser.rockets.details.injection.RocketInitData
import com.nekodev.rocketbrowser.util.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class RocketDetailsPresenter @Inject constructor(private val service: RocketService,
                                                 private val rocketInitData: RocketInitData,
                                                 private val schedulerProvider: BaseSchedulerProvider)
    : RocketDetailsContract.Presenter, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    companion object {
        private const val KEY_DESCRIPTION = "details"
        private const val KEY_LAUNCHES = "launches"
    }

    private var view: RocketDetailsContract.View? = null
    private val disposable = CompositeDisposable()
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
                log("fetch")
                val details = getDetails()
                delay(5000)
                log("fetched")
                withContext(Dispatchers.Main) {
                    log("show")
                    onDescriptionFetched(details.description)
                }
            } catch (e: Exception) {
                //jobCancellationException
                log("error ${e.localizedMessage} ${e.javaClass.simpleName}")
            }
        }
    }

    private fun log(message: String) {
        Log.d("RocketDetailsPresenter", "[${Thread.currentThread()}] $message")
    }

    private suspend fun getDetails(): RocketDetails {
        return service.getRocketDetails(rocketInitData.rocketId).await()
    }

    private fun showOrFetchLaunches() {
        launches?.let {
            onLaunchesFetched(it)
        } ?: run {
            fetchLaunches()
        }
    }

    private fun fetchLaunches() {
        view?.showProgress()
        disposable.add(service.getRocketLaunches(rocketInitData.rocketId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doFinally { view?.hideProgress() }
                .subscribeBy(
                        onSuccess = { onLaunchesFetched(it) },
                        onError = { onFetchLaunchesError() }
                ))
    }

    private fun onLaunchesFetched(launches: List<RocketLaunch>) {
        this.launches = launches
        val launchesAndYears = launches.groupBy { it.launchYear }
        view?.displayLaunches(launchesAndYears)
    }


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
        disposable.dispose()
        job.cancel()
    }

}