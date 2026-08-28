package com.najdev.scoreboard.stopwatchscreen

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

class StopwatchFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(
            ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
        )
        setContent {
            StopwatchScreen()
        }
    }
}

@Composable
fun StopwatchScreen() {
    var viewModel: StopwatchViewModel
    var isRunning by rememberSaveable { mutableStateOf(false) }
    var baseMs by rememberSaveable { mutableLongStateOf(0L) }
    var elapsedMs by rememberSaveable { mutableLongStateOf(0L) }
    var laps by rememberSaveable { mutableStateOf(listOf<Long>())}
    val displayMs = elapsedMs

    LaunchedEffect(isRunning, baseMs) {
        if (!isRunning) return@LaunchedEffect
        while (isRunning) {
            val now = SystemClock.elapsedRealtime()
            elapsedMs = now - baseMs
            delay(10.milliseconds)
        }
    }

    fun start() {
        if (!isRunning) {
            baseMs = SystemClock.elapsedRealtime() - elapsedMs
            isRunning = true
        }
    }

    fun stop() {
        isRunning = false
    }

    fun reset() {
        isRunning = false
        elapsedMs = 0
        laps = emptyList()
    }

    fun lap() {
        if (isRunning) laps = listOf(elapsedMs) + laps
    }

    fun formatMs(ms: Long): String {
        val totalSeconds = ms / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        val milliseconds = ms % 1000
        return "%02d:%02d.%02d".format(minutes, seconds, milliseconds)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = formatMs(displayMs), modifier = Modifier.padding(16.dp))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            if (!isRunning) {
                Button(onClick = { start() }) { Text(text = "Start") }
            } else {
                Button(onClick = { stop() }) { Text(text = "Stop") }
                OutlinedButton(
                    onClick = { lap() },
                ) { Text(text = "Lap") }
            }
            OutlinedButton(onClick = { reset() }, enabled = elapsedMs > 0) {
                Text(text = "Reset")
            }
        }
        Row() {
            if (laps.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    itemsIndexed(laps) { index, lapTime ->
                        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Tour #${laps.size - index}")
                                Text(formatMs(lapTime))
                            }
                        }
                    }
                }
            }
        }
    }


}