package com.nekodev.rocketbrowser.rockets.details.adapter.launch

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class LaunchDateFormat @Inject constructor() {

    val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.UK)

    fun format(timestampInSeconds: Long): String {
        return dateFormat.format(timestampInSeconds * 1_000)
    }
}