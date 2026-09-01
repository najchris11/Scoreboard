package com.najdev.scoreboard.stopwatchscreen

import kotlinx.coroutines.flow.StateFlow

interface StopwatchViewModel {

    val stopwatch: StateFlow<StopwatchModel>

    fun start()
    fun stop()
    fun reset()
    fun lap()

}