package com.najdev.scoreboard.scoreboardscreen

class ScoreboardAnalytics {
    suspend fun logScoreChange(team: TeamName, newScore: Int) {
        println("Score changed for $team: new score is $newScore")
    }
    suspend fun logTimeoutChange(team: TeamName, newTimeoutsTaken: Int) {
        println("Timeouts taken changed for $team: new timeouts taken is $newTimeoutsTaken")
    }
    suspend fun logGameReset() {
        println("Game has been reset")
    }
}