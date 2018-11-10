package com.nekodev.rocketbrowser.util

import android.util.Log
import kotlinx.coroutines.CoroutineScope

fun CoroutineScope.log(message: String) {
    Log.d("RocketBrowserLog", "[${Thread.currentThread()}] $message")
}
