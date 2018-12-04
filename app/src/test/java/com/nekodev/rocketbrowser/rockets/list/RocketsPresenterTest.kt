package com.nekodev.rocketbrowser.rockets.list

import com.nekodev.rocketbrowser.api.Rocket
import com.nekodev.rocketbrowser.api.RocketService
import com.nekodev.rocketbrowser.database.VisitDao
import com.nekodev.rocketbrowser.util.ImmediateSchedulerProvider
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.then
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.TestScheduler
import org.junit.Test
import java.util.concurrent.TimeUnit

class RocketsPresenterTest {

    private val service = mock<RocketService>()
    private val schedulerProvider = ImmediateSchedulerProvider()
    private val view = mock<RocketsContract.View>()
    private val dao = mock<VisitDao>()
    private val presenter = RocketsPresenter(service, schedulerProvider, dao)

    @Test
    fun `given rockets fetch success when subscribe then show dialog and fetch launches`() {
        //given
        val rockets = listOf(mock<Rocket>())
        given(service.getRockets()).willReturn(Single.just(rockets))
        given(dao.getAllVisits()).willReturn(Single.just(emptyList()))

        //when
        presenter.subscribe(view)

        //then
        then(view).should().showWelcomeDialog(0)
        then(view).should().showRockets(rockets)
    }

    @Test
    fun `given rockets fetch error when subscribe then show dialog and show error`() {
        //given
        given(service.getRockets()).willReturn(Single.error(Throwable()))
        given(dao.getAllVisits()).willReturn(Single.just(emptyList()))

        //when
        presenter.subscribe(view)

        //then
        then(view).should().showWelcomeDialog(0)
        then(view).should().showError()
    }

    @Test
        fun switchMapTest() {
        val items = listOf("a", "b", "c", "d", "e", "f")
        val scheduler = TestScheduler()

        Observable.fromArray(items)
                .flatMapIterable {it}
                .switchMap {
                    Observable.just(it + "x")
                            .delay(1, TimeUnit.SECONDS, scheduler)
                }
                .subscribeBy(
                    onNext = { println(it)},
                        onComplete = {println("success")}
                )

        scheduler.advanceTimeBy(1, TimeUnit.MINUTES)
    }
}