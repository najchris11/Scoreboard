package com.najdev.scoreboard.scoreboardscreen.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.najdev.scoreboard.scoreboardscreen.ScoreboardAnalytics
import com.najdev.scoreboard.scoreboardscreen.ScoreboardModel
import com.najdev.scoreboard.scoreboardscreen.ScoreboardViewModel
import com.najdev.scoreboard.scoreboardscreen.Team
import com.najdev.scoreboard.scoreboardscreen.TeamName
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ScoreboardViewModelImpl : ViewModel(), ScoreboardViewModel {
    private val _scoreboard = MutableStateFlow(
        ScoreboardModel(
            home = Team(score = 0, timeoutsTaken = 0),
            away = Team(score = 0, timeoutsTaken = 0)
        )
    )
    override val scoreboard = _scoreboard.asStateFlow()
    private val analytics = ScoreboardAnalytics()

    private fun updateTeam(team: TeamName, update: (Team) -> Team): ScoreboardModel {
        val current = _scoreboard.value
        val updated = when (team) {
            TeamName.AWAY -> current.copy(away = update(current.away))
            TeamName.HOME -> current.copy(home = update(current.home))
        }
        _scoreboard.value = updated
        return updated
    }

    private fun teamOf(model: ScoreboardModel, team: TeamName): Team {
        return when (team) {
            TeamName.AWAY -> model.away
            TeamName.HOME -> model.home
        }
    }
    override fun increaseScore(team: TeamName) {
        val updated = updateTeam(team) { it.copy(score = it.score + 1) }
        viewModelScope.launch {
            analytics.logScoreChange(team, teamOf(updated, team).score)
        }
    }

    override fun decreaseScore(team: TeamName) {
        val current = teamOf(_scoreboard.value, team)
        if (current.score == 0) return
        val updated = updateTeam(team) { it.copy(score = it.score - 1) }
        viewModelScope.launch { analytics.logScoreChange(team, teamOf(updated, team).score) }
    }

    override fun increaseTimeout(team: TeamName) {
        val current = teamOf(_scoreboard.value, team)
        if (current.timeoutsTaken == 3) return
        val updated = updateTeam(team) {it.copy(timeoutsTaken = it.timeoutsTaken + 1)}
        viewModelScope.launch {
            analytics.logTimeoutChange(team, teamOf(updated, team).timeoutsTaken)
        }
    }

    override fun decreaseTimeout(team: TeamName) {
        val current = teamOf(_scoreboard.value, team)
        if (current.timeoutsTaken == 0) return
        val updated = updateTeam(team) {it.copy(timeoutsTaken = it.timeoutsTaken - 1)}
        viewModelScope.launch {
            analytics.logTimeoutChange(team, teamOf(updated, team).timeoutsTaken)
        }
    }

    override fun resetGame() {
        _scoreboard.value = ScoreboardModel(
            home = Team(score = 0, timeoutsTaken = 0),
            away = Team(score = 0, timeoutsTaken = 0)
        )
        viewModelScope.launch {
            analytics.logGameReset()
        }
    }
}