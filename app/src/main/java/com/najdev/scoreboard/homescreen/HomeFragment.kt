package com.najdev.scoreboard.homescreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.najdev.scoreboard.R

class HomeFragment : Fragment(R.layout.home_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<android.widget.Button>(R.id.btn_scoreboard).setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_scoreboardFragment)
        }
        view.findViewById<android.widget.Button>(R.id.btn_timer).setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_timerFragment)
        }
    }
}