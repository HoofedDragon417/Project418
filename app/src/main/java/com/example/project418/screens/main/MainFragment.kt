package com.example.project418.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.project418.R
import com.example.project418.adapters.pager.ViewPagerAdapter
import com.example.project418.common.BaseFragment
import com.example.project418.databinding.FragmentMainBinding
import com.example.project418.tabs.journal.JournalFragment
import com.example.project418.tabs.students.StudentsFragment
import com.example.project418.tabs.subjects.SubjectsFragment
import com.example.project418.tabs.teachers.TeachersFragment
import com.google.android.material.tabs.TabLayoutMediator

//TODO: Почитать про insets

class MainFragment : BaseFragment() {

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

        val tabs = listOf(
            getString(R.string.teachers_title),
            getString(R.string.student_title),
            getString(R.string.subject_title),
            getString(R.string.journal_title)
        )

        val fragments =
            listOf(TeachersFragment(), StudentsFragment(), SubjectsFragment(), JournalFragment())

        binding.pager.adapter = ViewPagerAdapter(requireActivity(), fragments)

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = tabs[position]
        }.attach()

        binding.fabTest.setOnClickListener {
            if (binding.fabCreateQr.isVisible)
                binding.fabCreateQr.hide()
            else binding.fabCreateQr.show()

            if (binding.fabScanQr.isVisible)
                binding.fabScanQr.hide()
            else binding.fabScanQr.show()

            if (binding.fabLogout.isVisible)
                binding.fabLogout.hide()
            else binding.fabLogout.show()
        }

        binding.fabCreateQr.setOnClickListener {
            viewModel.createQr()
        }

        binding.fabScanQr.setOnClickListener {
            viewModel.scanQr()
        }

        binding.fabLogout.setOnClickListener {
            viewModel.logout()
        }
    }
}