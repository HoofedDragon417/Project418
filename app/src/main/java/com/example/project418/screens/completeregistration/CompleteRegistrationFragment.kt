package com.example.project418.screens.completeregistration

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.project418.R
import com.example.project418.common.BaseFragment
import com.example.project418.databinding.FragmentCompleteRegistrationBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CompleteRegistrationFragment(private val qrContent: String) : BaseFragment() {

    private var _binding: FragmentCompleteRegistrationBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: CompleteRegistrationVM by viewModels {
        CompleteRegistrationVM.Factory(
            requireContext().applicationContext
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        router?.let(viewModel::setRouter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getQrContent(qrContent)
        _binding = FragmentCompleteRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscription()

        binding.etTeacher.doAfterTextChanged { s ->
            viewModel.getTeacherPosition(s.toString())
        }

        binding.etTeacher.addTextChangedListener {
            viewModel.errorNull()
        }

        binding.btn.setOnClickListener {
            val title = binding.etTitleOfWork.text.toString()
            viewModel.registrationWork(title)
        }
    }

    private fun subscription() {
        lifecycleScope.launch {
            viewModel.student.onEach {
                binding.tvStudent.setText(it)
            }.collect()
        }

        lifecycleScope.launch {
            viewModel.subject.onEach {
                binding.tvSubject.setText(it)
            }.collect()
        }

        lifecycleScope.launch {
            viewModel.typeOfWork.onEach {
                binding.tvTypeOfWork.setText(it)
            }.collect()
        }

        lifecycleScope.launch {
            viewModel.stringListOfTeachers.onEach {
                val adapter = ArrayAdapter(requireContext(), R.layout.dropmenu_item, it)

                binding.etTeacher.setAdapter(adapter)
            }.collect()
        }

        lifecycleScope.launch {
            viewModel.errorMessage.onEach {
                binding.teacherContainer.error = it
            }.collect()
        }
    }
}