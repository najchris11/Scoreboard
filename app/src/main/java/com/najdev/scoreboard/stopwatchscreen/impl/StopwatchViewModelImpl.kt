package com.najdev.scoreboard.stopwatchscreen.impl

import androidx.lifecycle.ViewModel
import com.najdev.scoreboard.stopwatchscreen.StopwatchViewModel

class StopwatchViewModelImpl: ViewModel(), StopwatchViewModel {
    private var isRunning = false
    private var baseMs = 0L
    private var elapsedMs = 0L

    override fun start(currentTimeMs: Long) {
        if (!isRunning) {
            baseMs = currentTimeMs - elapsedMs
            isRunning = true
        }
    }

    override fun updateTime(currentTimeMs: Long): Long {
        return if (isRunning) {
            elapsedMs = currentTimeMs - baseMs
            elapsedMs
        } else elapsedMs
    }
}