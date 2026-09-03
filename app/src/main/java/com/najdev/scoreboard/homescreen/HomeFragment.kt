package com.najdev.scoreboard.homescreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.najdev.scoreboard.R
import com.najdev.scoreboard.homescreen.impl.HomeViewModelImpl
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.home_fragment) {
    val viewModel by viewModels<HomeViewModelImpl>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<android.widget.Button>(R.id.btn_scoreboard).setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_scoreboardFragment)
        }
        view.findViewById<android.widget.Button>(R.id.btn_timer).setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_timerFragment)
        }
        val sponsorTextView = view.findViewById<android.widget.TextView>(R.id.sponsorText)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sponsorName.collect {
                    sponsorTextView.text = it
                }
            }
        }

    }
}