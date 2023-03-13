package com.example.project418.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.project418.R
import com.example.project418.common.Screens
import com.example.project418.databinding.FragmentLoginBinding
import com.example.project418.databinding.FragmentMainBinding
import com.example.project418.storage.SharedPreference
import com.github.terrakok.cicerone.Router

class MainFragment(private val router: Router) : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogout.setOnClickListener {
            SharedPreference(requireContext()).clearID()
            router.newRootScreen(Screens.Login(router))
        }
    }
}