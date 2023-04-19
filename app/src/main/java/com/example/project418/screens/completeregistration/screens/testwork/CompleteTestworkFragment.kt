package com.example.project418.screens.completeregistration.screens.testwork

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.project418.R
import com.example.project418.databinding.FragmentCompleteTestworkBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class CompleteTestworkFragment : Fragment() {

    private var _binding: FragmentCompleteTestworkBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: CompleteTestworkVM by activityViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString(QR_CONTENT)?.let { viewModel.getQrContent(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompleteTestworkBinding.inflate(inflater, container, false)
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
            viewModel.registrationWork()
        }
    }

    private fun subscription() {
        viewModel.student.onEach { student ->
            binding.tvStudent.setText(student)
        }.launchIn(lifecycleScope)

        viewModel.subject.onEach { subject ->
            binding.tvSubject.setText(subject)
        }.launchIn(lifecycleScope)

        viewModel.typeOfWork.onEach { typeOfWork ->
            binding.tvTypeOfWork.setText(typeOfWork)
        }.launchIn(lifecycleScope)

        viewModel.stringListOfTeachers.onEach { list ->
            val adapter = ArrayAdapter(requireContext(), R.layout.dropmenu_item, list)

            binding.etTeacher.setAdapter(adapter)
        }.launchIn(lifecycleScope)

        viewModel.errorTeacherMessage.onEach { message ->
            binding.teacherContainer.error = message
        }.launchIn(lifecycleScope)

        viewModel.errorInsertMessage.onEach { showError ->
            if (showError)
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(R.string.qr_error_title)
                    .setMessage(R.string.insert_error_message)
                    .setPositiveButton("OK") { dialog, which ->
                        dialog.dismiss()
                    }.create().show()
        }.launchIn(lifecycleScope)
    }

    companion object {
        private const val QR_CONTENT = "qr_content"

        fun newInstance(content: String) =
            CompleteTestworkFragment().apply {
                arguments = bundleOf(
                    QR_CONTENT to content
                )
            }
    }
}