package com.najdev.scoreboard.scoreboardscreen

import kotlinx.coroutines.flow.StateFlow

interface ScoreboardViewModel {
    val scoreboard: StateFlow<ScoreboardModel>
    fun increaseScore(team: TeamName)
    fun decreaseScore(team: TeamName)
    fun increaseTimeout(team: TeamName)
    fun decreaseTimeout(team: TeamName)
    fun resetGame()
}