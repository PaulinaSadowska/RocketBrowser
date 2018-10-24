package com.nekodev.rocketbrowser.rockets.details.adapter

import com.nekodev.rocketbrowser.api.RocketLaunch
import com.nekodev.rocketbrowser.rockets.details.adapter.launch.RocketLauchItem
import com.nekodev.rocketbrowser.rockets.details.adapter.year.YearItem
import com.nhaarman.mockito_kotlin.mock
import org.junit.Assert.*
import org.junit.Test


class LaunchesAndYearsToViewTypeConverterTest {

    private val converter = LaunchesAndYearsToViewTypeConverter()

    @Test
    fun `given empty map when convert then empty list`() {
        //when
        val result = converter.convert(emptyMap())

        //then
        assertEquals(result.size, 0)
    }

    @Test
    fun `given non empty map when convert then list with keys and items`() {
        //when
        val launchesList : List<RocketLaunch> = listOf(mock(), mock())
        val year = "2011"
        val map = mapOf(year to launchesList)
        val result = converter.convert(map)

        //then
        assertEquals(result.size, 3)
        assertTrue(result[0] is YearItem)
        assertTrue(result[1] is RocketLauchItem)
        assertTrue(result[2] is RocketLauchItem)
    }
}