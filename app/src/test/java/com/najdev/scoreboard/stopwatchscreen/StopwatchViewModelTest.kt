package com.najdev.scoreboard.stopwatchscreen

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class StopwatchViewModelTest {
    @Test
    fun `timer updates correctly with virtual time`() {
        val logic = StopwatchViewModel()

        logic.start(0L)
        assertEquals(1000L, logic.updateTime(1000L))
        assertEquals(2500L, logic.updateTime(2500L))
    }

    @Test
    fun `timer is always monotonic`() {
        val logic = StopwatchViewModel()
        var lastTime = 0L

        logic.start(0L)
        repeat(100) { iteration ->
            val currentTime = logic.updateTime(iteration * 100L)
            assertTrue(currentTime >= lastTime)
            lastTime = currentTime
        }
    }
}