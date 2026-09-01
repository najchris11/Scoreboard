package com.najdev.scoreboard.stopwatchscreen

data class StopwatchModel(val isRunning: Boolean = false,
                          val baseMs: Long = 0,
                          val elapsedMs: Long = 0,
                          val laps: List<Long> = listOf()) {}