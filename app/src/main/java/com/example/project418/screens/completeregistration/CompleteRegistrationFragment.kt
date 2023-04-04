package com.example.project418.screens.completeregistration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.project418.R
import com.example.project418.common.BaseFragment
import com.example.project418.databinding.FragmentCompleteRegistrationBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

private const val QR_CONTENT = "qr_content"

class CompleteRegistrationFragment : BaseFragment() {

    private var _binding: FragmentCompleteRegistrationBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: CompleteRegistrationVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString(QR_CONTENT)?.let { viewModel.getQrContent(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompleteRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscription()

        binding.etTeacher.setOnClickListener {
            if (binding.teacherContainer.error != null)
                viewModel.errorNull()
        }

        binding.etTeacher.doAfterTextChanged { s ->
            viewModel.getTeacherPosition(s.toString())
        }

        binding.btnCompleteRegistration.setOnClickListener {
            val title = binding.etTitleOfWork.text.toString()
            viewModel.registrationWork(title)
        }
    }

    private fun subscription() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.student.onEach {
                    binding.tvStudent.setText(it)
                }.collect()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.subject.onEach {
                    binding.tvSubject.setText(it)
                }.collect()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.typeOfWork.onEach {
                    binding.tvTypeOfWork.setText(it)
                }.collect()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stringListOfTeachers.onEach {
                    val adapter = ArrayAdapter(requireContext(), R.layout.dropmenu_item, it)

                    binding.etTeacher.setAdapter(adapter)
                }.collect()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.errorMessage.onEach {
                    binding.teacherContainer.error = it
                }.collect()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.titleEnable.onEach {
                    binding.titleContainer.isEnabled = it
                }.collect()
            }
        }
    }

    companion object {
        fun newInstance(content: String) = CompleteRegistrationFragment().apply {
            arguments = bundleOf(
                QR_CONTENT to content
            )
        }
    }

}