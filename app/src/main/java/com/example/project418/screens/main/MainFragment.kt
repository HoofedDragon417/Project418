package com.example.project418.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.project418.R
import com.example.project418.adapters.pager.ViewPagerAdapter
import com.example.project418.databinding.FragmentMainBinding
import com.example.project418.screens.students.StudentsFragment
import com.example.project418.screens.subjects.SubjectsFragment
import com.example.project418.screens.teachers.TeachersFragment
import com.github.terrakok.cicerone.Router
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment(private val router: Router) : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: MainVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragments = listOf(TeachersFragment(), StudentsFragment(), SubjectsFragment())

        val tabs = listOf(
            getString(R.string.teachers_title),
            getString(R.string.student_title),
            getString(R.string.subject_title)
        )

        binding.pager.adapter = ViewPagerAdapter(requireActivity(), fragments)

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = tabs[position]
        }.attach()
    }
}