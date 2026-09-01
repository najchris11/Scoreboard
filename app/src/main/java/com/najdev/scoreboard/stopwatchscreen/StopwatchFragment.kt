package com.najdev.scoreboard.stopwatchscreen

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.najdev.scoreboard.stopwatchscreen.impl.StopwatchViewModelImpl
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.compose.collectAsStateWithLifecycle

class StopwatchFragment: Fragment() {
    val viewModel: StopwatchViewModel by viewModels<StopwatchViewModelImpl>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(
            ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
        )
        setContent {
            StopwatchScreen(viewModel = viewModel)
        }
    }
}

@Composable
fun StopwatchScreen(viewModel: StopwatchViewModel) {
    val state by viewModel.stopwatch.collectAsStateWithLifecycle()

    fun formatMs(ms: Long): String {
        val totalSeconds = ms / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        val milliseconds = ms % 1000
        return "%02d:%02d.%02d".format(minutes, seconds, milliseconds/10)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = formatMs(state.elapsedMs), modifier = Modifier.padding(16.dp))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            if (!state.isRunning) {
                Button(onClick = { viewModel.start() }) { Text(text = "Start") }
            } else {
                Button(onClick = { viewModel.stop() }) { Text(text = "Stop") }
                OutlinedButton(
                    onClick = { viewModel.lap() },
                ) { Text(text = "Lap") }
            }
            OutlinedButton(onClick = { viewModel.reset() }, enabled = state.elapsedMs > 0) {
                Text(text = "Reset")
            }
        }
        Row() {
            if (state.laps.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    itemsIndexed(state.laps) { index, lapTime ->
                        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Lap #${state.laps.size - index}")
                                Text(formatMs(lapTime))
                            }
                        }
                    }
                }
            }
        }
    }


}
//
//@Preview
//@Composable
//fun StopwatchScreenPreview() {
//    StopwatchScreen(viewModel = TODO())
//}