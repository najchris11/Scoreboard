package com.najdev.scoreboard.stopwatchscreen.impl

import android.os.SystemClock
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.najdev.scoreboard.stopwatchscreen.StopwatchModel
import com.najdev.scoreboard.stopwatchscreen.StopwatchViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class StopwatchViewModelImpl: ViewModel(), StopwatchViewModel {
    private val _stopwatch = MutableStateFlow(StopwatchModel())
    override val stopwatch = _stopwatch.asStateFlow()
    var timerJob: Job? = null

    override fun start() {
        if (!_stopwatch.value.isRunning) {
            _stopwatch.update {
                it.copy(baseMs = SystemClock.elapsedRealtime() - it.elapsedMs, isRunning = true)
            }
            timerJob = viewModelScope.launch {
                while (_stopwatch.value.isRunning) {
                    val now = SystemClock.elapsedRealtime()
                    _stopwatch.update {
                        it.copy(elapsedMs = now - it.baseMs)
                    }
                    kotlinx.coroutines.delay(10.milliseconds)
                }

            }
        }
    }

    override fun stop() {
        _stopwatch.update {
            it.copy(isRunning = false)
        }
        timerJob?.cancel()
        timerJob = null
    }

    override fun reset() {
        _stopwatch.update {
            it.copy(isRunning = false, elapsedMs = 0, laps = emptyList())
        }
    }

    override fun lap() {
        if (_stopwatch.value.isRunning) {
            _stopwatch.update {
                it.copy(laps = listOf(it.elapsedMs) + it.laps)
            }
        }
    }
}