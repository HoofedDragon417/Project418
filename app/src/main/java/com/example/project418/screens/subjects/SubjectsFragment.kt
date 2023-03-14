package com.example.project418.screens.subjects

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.project418.adapters.recycler.SubjectAdapter
import com.example.project418.databinding.FragmentSubjectsBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

class SubjectsFragment : Fragment() {

    private var _binding: FragmentSubjectsBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: SubjectVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getSubjects(requireContext())
        _binding = FragmentSubjectsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.listOfSubjects.onEach {
                binding.subjectRecycler.adapter = SubjectAdapter(requireContext(), it)
            }.collect()
        }
    }
}