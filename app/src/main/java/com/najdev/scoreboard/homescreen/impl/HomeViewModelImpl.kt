package com.najdev.scoreboard.homescreen.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.najdev.scoreboard.homescreen.HomeApi
import com.najdev.scoreboard.homescreen.HomeViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModelImpl: HomeViewModel, ViewModel() {
    private val api = HomeApi()
    private val _sponsorName = MutableStateFlow<String>("Sponsor Name")
    override val sponsorName = _sponsorName.asStateFlow()

    init {
        viewModelScope.launch {
            val sponsorNameDeferred = async {api.getSponsorName()}
            _sponsorName.value = sponsorNameDeferred.await()
        }
    }
}