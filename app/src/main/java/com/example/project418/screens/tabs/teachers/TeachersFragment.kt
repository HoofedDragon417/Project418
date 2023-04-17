package com.example.project418.screens.tabs.teachers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.project418.adapters.recycler.TeachersAdapter
import com.example.project418.databinding.FragmentRecyclerBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class TeachersFragment : Fragment() {

    private var _binding: FragmentRecyclerBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: TeachersVM by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getTeachers()
        _binding = FragmentRecyclerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.listOfTeachers.onEach {
                binding.recycler.adapter = TeachersAdapter(requireContext(), it)
            }.collect()
        }
    }
}