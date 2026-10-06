package com.najdev.scoreboard.stopwatchscreen

import com.najdev.scoreboard.stopwatchscreen.helper.ElapsedTimeProvider
import com.najdev.scoreboard.stopwatchscreen.impl.StopwatchViewModelImpl
import io.mockk.every
import io.mockk.mockkStatic
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test

class StopwatchViewModelTest {
    private lateinit var viewModel: StopwatchViewModel
    private lateinit var fakeClock: FakeClock

    class FakeClock(private var nowMs: Long = 1000L) : ElapsedTimeProvider{
        override fun now() = nowMs
        fun advance(ms: Long) {
            nowMs += ms
        }
    }

    @Before
    fun setUp() {
        fakeClock = FakeClock()
        viewModel = StopwatchViewModelImpl(fakeClock)
//        mockkStatic("android.os.SystemClock")
//        every { android.os.SystemClock.elapsedRealtime() } returns 1000L
    }

    @Test
    fun `elapsedTime is 0 before stopwatch start`() {
        assertEquals(0, viewModel.stopwatch.value.elapsedMs)
    }

    @Test
    fun `isRunning is always factual`() {
        assertFalse(viewModel.stopwatch.value.isRunning)
        viewModel.start()
        fakeClock.advance(1000L)
        assertTrue(viewModel.stopwatch.value.isRunning)
        viewModel.stop()
        assertFalse(viewModel.stopwatch.value.isRunning)
    }

    @Test
    fun `stop() stops the stopwatch`() {
        viewModel.start()
        fakeClock.advance(1000L)
        viewModel.stop()
        val cp1 = viewModel.stopwatch.value.elapsedMs
        val cp2 = viewModel.stopwatch.value.elapsedMs
        assertEquals(cp1, cp2)
    }

    @Test
    fun `start() increases elapsedTime`() {
        val startTime = viewModel.stopwatch.value.elapsedMs
        viewModel.start()
        fakeClock.advance(1000L)
        val endTime = viewModel.stopwatch.value.elapsedMs
        assertTrue(endTime > startTime)
    }

}