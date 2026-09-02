package com.najdev.scoreboard.scoreboardscreen.impl

import androidx.lifecycle.ViewModel
import com.najdev.scoreboard.scoreboardscreen.ScoreboardModel
import com.najdev.scoreboard.scoreboardscreen.ScoreboardViewModel
import com.najdev.scoreboard.scoreboardscreen.Team
import com.najdev.scoreboard.scoreboardscreen.TeamName
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ScoreboardViewModelImpl : ViewModel(), ScoreboardViewModel {

    private val _scoreboard = MutableStateFlow(
        ScoreboardModel(
            home = Team(score = 0, timeoutsTaken = 0),
            away = Team(score = 0, timeoutsTaken = 0)
        )
    )
    override val scoreboard = _scoreboard.asStateFlow()
    override fun increaseScore(team: TeamName) {
        _scoreboard.value = when (team) {
            TeamName.HOME -> _scoreboard.value.copy(home = _scoreboard.value.home.copy(score = _scoreboard.value.home.score + 1))
            TeamName.AWAY -> _scoreboard.value.copy(away = _scoreboard.value.away.copy(score = _scoreboard.value.away.score + 1))
        }
    }

    override fun decreaseScore(team: TeamName) {
        _scoreboard.value = when (team) {
            TeamName.HOME -> {if (_scoreboard.value.home.score > 0)
                _scoreboard.value.copy(home = _scoreboard.value.home.copy(score = _scoreboard.value.home.score - 1)) else return
            }
            TeamName.AWAY -> { if (_scoreboard.value.away.score > 0) _scoreboard.value.copy(away = _scoreboard.value.away.copy(score = _scoreboard.value.away.score - 1)) else return }
        }
    }

    override fun increaseTimeout(team: TeamName) {
        _scoreboard.value = when (team) {
            TeamName.HOME -> {if (_scoreboard.value.home.timeoutsTaken < 3) _scoreboard.value.copy(home = _scoreboard.value.home.copy(timeoutsTaken = _scoreboard.value.home.timeoutsTaken + 1)) else return}
            TeamName.AWAY -> {if (_scoreboard.value.away.timeoutsTaken < 3) _scoreboard.value.copy(away = _scoreboard.value.away.copy(timeoutsTaken = _scoreboard.value.away.timeoutsTaken + 1)) else return}
        }
    }

    override fun decreaseTimeout(team: TeamName) {
        _scoreboard.value = when (team) {
            TeamName.HOME -> {if (_scoreboard.value.home.timeoutsTaken > 0)_scoreboard.value.copy(home = _scoreboard.value.home.copy(timeoutsTaken = _scoreboard.value.home.timeoutsTaken - 1)) else return}
            TeamName.AWAY -> {if (_scoreboard.value.away.timeoutsTaken > 0)_scoreboard.value.copy(away = _scoreboard.value.away.copy(timeoutsTaken = _scoreboard.value.away.timeoutsTaken - 1)) else return}
        }
    }

    override fun resetGame() {
        _scoreboard.value = ScoreboardModel(
            home = Team(score = 0, timeoutsTaken = 0),
            away = Team(score = 0, timeoutsTaken = 0)
        )
    }
}