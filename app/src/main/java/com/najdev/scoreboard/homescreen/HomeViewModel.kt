package com.najdev.scoreboard.homescreen

import kotlinx.coroutines.flow.StateFlow

interface HomeViewModel {
    val sponsorName: StateFlow<String>
}