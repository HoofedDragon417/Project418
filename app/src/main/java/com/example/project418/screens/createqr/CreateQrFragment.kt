package com.example.project418.screens.createqr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.project418.R
import com.example.project418.common.BaseFragment
import com.example.project418.databinding.FragmentCreateQrBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class CreateQrFragment : BaseFragment() {

    private var _binding: FragmentCreateQrBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: CreateQrVM by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getLists()
        _binding = FragmentCreateQrBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscription()

        binding.subjectContainer.setOnClickListener {
            viewModel.cleanSubjectError()
        }

        binding.etStudent.doAfterTextChanged { s ->
            viewModel.cleanStudentError()
            viewModel.getStudentIndex(s.toString())
        }

        binding.etSubject.doAfterTextChanged { s ->
            viewModel.cleanSubjectError()
            viewModel.getSubjectIndex(s.toString())
        }

        binding.btnGenerateQr.setOnClickListener {
            viewModel.generateQr()
        }
    }

    private fun subscription() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stringListOfSubjects.onEach {
                    val adapter = ArrayAdapter(requireContext(), R.layout.dropmenu_item, it)

                    binding.etSubject.setAdapter(adapter)
                }.collect()
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stringListOfStudents.onEach {
                    val adapter = ArrayAdapter(requireContext(), R.layout.dropmenu_item, it)

                    binding.etStudent.setAdapter(adapter)
                }.collect()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.showStudentError.onEach {
                    binding.studentContainer.error = it
                }.collect()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.showSubjectError.onEach {
                    binding.subjectContainer.error = it
                }.collect()
            }
        }
    }
}