package com.example.project418.screens.teachers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.project418.adapters.recycler.TeachersAdapter
import com.example.project418.databinding.FragmentTeachersBinding
import com.example.project418.models.Teachers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

class TeachersFragment : Fragment() {

    private var _binding: FragmentTeachersBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: TeachersVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getTeachers(requireContext())
        _binding = FragmentTeachersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.listOfTeachers.onEach {
                binding.teachersRecycler.adapter = TeachersAdapter(requireContext(), it)
            }.collect()
        }
    }
}