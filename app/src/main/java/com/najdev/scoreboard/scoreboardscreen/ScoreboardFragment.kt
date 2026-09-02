package com.najdev.scoreboard.scoreboardscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.najdev.scoreboard.R
import com.najdev.scoreboard.scoreboardscreen.impl.ScoreboardViewModelImpl
import com.najdev.scoreboard.stopwatchscreen.StopwatchViewModel
import com.najdev.scoreboard.stopwatchscreen.impl.StopwatchViewModelImpl
import org.intellij.lang.annotations.JdkConstants
import kotlin.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

class ScoreboardFragment: Fragment() {
    val viewModel: ScoreboardViewModel by viewModels<ScoreboardViewModelImpl>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            ScoreboardScreen(viewModel)
        }
    }
}

@Composable
fun ScoreboardScreen(viewModel: ScoreboardViewModel) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TeamInterface("Home", viewModel, TeamName.HOME)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TeamInterface("Away", viewModel, TeamName.AWAY)
            }
        }
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = {
                viewModel.resetGame()
            }) {
                Text("Reset")
            }
        }
    }

}

@Composable
fun TeamInterface(teamName: String, viewModel: ScoreboardViewModel, team: TeamName) {
    val state by viewModel.scoreboard.collectAsStateWithLifecycle()
    Text(teamName)
    Text("Scores")
    Row() {
        Button(onClick = {
            when (team) {
                TeamName.AWAY -> viewModel.decreaseScore(TeamName.AWAY)
                TeamName.HOME -> viewModel.decreaseScore(TeamName.HOME)
            }
        }) {
            Text("-1")
        }
        Text(
            when (team) {
                TeamName.AWAY -> state.away.score.toString()
                TeamName.HOME -> state.home.score.toString()
            }
        )
        Button(onClick = {
            when (team) {
                TeamName.AWAY -> viewModel.increaseScore(TeamName.AWAY)
                TeamName.HOME -> viewModel.increaseScore(TeamName.HOME)
            }
        }) {
            Text("+1")
        }
    }
    Text("Timeouts")
    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
        Button(onClick = {
            when (team) {
                TeamName.AWAY -> viewModel.decreaseTimeout(TeamName.AWAY)
                TeamName.HOME -> viewModel.decreaseTimeout(TeamName.HOME)
            }
        }) {
            Text("-1")
        }
        repeat(3){ index ->
            when (team) {
                TeamName.AWAY -> {
                    val taken = index < state.away.timeoutsTaken
                    Surface(
                        modifier = Modifier
                            .size(20.dp)
                            .padding(4.dp),
                        color = if (taken) Color.Red else Color.Gray
                    ) {
                    }
                }

                TeamName.HOME -> {
                    val taken = index < state.home.timeoutsTaken
                    Surface(
                        modifier = Modifier
                            .size(20.dp)
                            .padding(4.dp),
                        color = if (taken) Color.Red else Color.Gray
                    ) {
                    }
                }
            }

        }
        Button(onClick = {
            when (team) {
                TeamName.AWAY -> viewModel.increaseTimeout(TeamName.AWAY)
                TeamName.HOME -> viewModel.increaseTimeout(TeamName.HOME)
            }
        }) {
            Text("+1")
        }
    }
}

//@Preview
//@Composable
//fun ScoreboardScreenPreview() {
//    ScoreboardScreen()
//}