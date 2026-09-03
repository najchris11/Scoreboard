package com.najdev.scoreboard.homescreen

import kotlinx.coroutines.delay

class HomeApi {
    suspend fun getSponsorName(): String {
        delay(2000)
        return "DONATE To Advertise"
    }
}