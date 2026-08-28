package com.najdev.scoreboard.stopwatchscreen
interface StopwatchViewModel {
    fun start(currentTimeMs: Long)
    fun updateTime(currentTimeMs: Long): Long
}