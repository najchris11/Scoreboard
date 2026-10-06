package com.najdev.scoreboard.stopwatchscreen.helper

import android.os.SystemClock

interface ElapsedTimeProvider {
    fun now(): Long
}

object  AndroidElapsedTimeProvider: ElapsedTimeProvider {
    override fun now(): Long {
        return SystemClock.elapsedRealtime()
    }
}