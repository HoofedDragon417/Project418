package com.example.project418.screens.students

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.project418.adapters.recycler.StudentAdapter
import com.example.project418.databinding.FragmentStudentsBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

class StudentsFragment : Fragment() {

    private var _binding: FragmentStudentsBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: StudentsVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getStudents(requireContext())
        _binding = FragmentStudentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.listOfStudents.onEach {
                binding.studentRecycler.adapter = StudentAdapter(requireContext(), it)
            }.collect()
        }
    }
}