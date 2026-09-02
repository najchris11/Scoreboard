package com.najdev.scoreboard.scoreboardscreen

data class ScoreboardModel (
    val home: Team,
    val away: Team
)
data class Team(
    val score: Int,
    val timeoutsTaken: Int
)
enum class TeamName{
    HOME,
    AWAY
}