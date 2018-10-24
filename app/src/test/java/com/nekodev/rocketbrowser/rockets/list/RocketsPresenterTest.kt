package com.nekodev.rocketbrowser.rockets.list

import com.nekodev.rocketbrowser.api.Rocket
import com.nekodev.rocketbrowser.api.RocketService
import com.nekodev.rocketbrowser.util.ImmediateSchedulerProvider
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.then
import io.reactivex.Single
import org.junit.Test

class RocketsPresenterTest {

    private val service = mock<RocketService>()
    private val schedulerProvider = ImmediateSchedulerProvider()
    private val view = mock<RocketsContract.View>()
    private val presenter = RocketsPresenter(service, schedulerProvider)

    @Test
    fun `given rockets fetch success when subscribe then show dialog and fetch launches`() {
        //given
        val rockets = listOf(mock<Rocket>())
        given(service.getRockets()).willReturn(Single.just(rockets))

        //when
        presenter.subscribe(view)

        //then
        then(view).should().showWelcomeDialog()
        then(view).should().showRockets(rockets)
    }

    @Test
    fun `given rockets fetch error when subscribe then show dialog and show error`() {
        //given
        given(service.getRockets()).willReturn(Single.error(Throwable()))

        //when
        presenter.subscribe(view)

        //then
        then(view).should().showWelcomeDialog()
        then(view).should().showError()
    }
}