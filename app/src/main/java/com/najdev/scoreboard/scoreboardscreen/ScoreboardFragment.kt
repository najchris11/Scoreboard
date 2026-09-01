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
import com.najdev.scoreboard.R
import org.intellij.lang.annotations.JdkConstants

class ScoreboardFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            ScoreboardScreen()
        }
    }
}

@Composable
fun ScoreboardScreen() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TeamInterface("Home")
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TeamInterface("Away")
        }
    }
}

@Composable
fun TeamInterface(teamName: String) {
    Text(teamName)
    Text("Scores")
    Row() {
        Button(onClick = { }) {
            Text("-1")
        }
        Text("0")
        Button(onClick = { }) {
            Text("+1")
        }
    }
    Text("Timeouts")
    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
        Button(onClick = { }) {
            Text("-1")
        }
        repeat(3){
            Surface(
                modifier = Modifier
                    .size(20.dp)
                    .padding(4.dp),
                color = Color.Gray
            ) {
            }
        }
        Button(onClick = { }) {
            Text("-1")
        }
    }
    Row() {
        Button(onClick = {}) {
            Text("Reset")
        }
    }
}

@Preview
@Composable
fun ScoreboardScreenPreview() {
    ScoreboardScreen()
}