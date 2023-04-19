package com.example.project418.screens.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.project418.R
import com.example.project418.common.AppGlobal
import com.example.project418.common.BaseFragment
import com.example.project418.databinding.FragmentLoginBinding
import com.example.project418.models.Department
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class LoginFragment : BaseFragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: LoginVM by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscription()

        binding.btnLogin.setOnClickListener {
            val userLogin = binding.etLogin.text.toString()
            val userPassword = binding.etPassword.text.toString()

            viewModel.authorization(Department(login = userLogin, password = userPassword))
        }
    }

    private fun subscription() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.showAlertDialog.onEach { needToShow ->
                    if (needToShow) {
                        val alertDialog = MaterialAlertDialogBuilder(requireContext())
                            .setTitle(AppGlobal.Instance.getString(R.string.login_error_title))
                            .setMessage(AppGlobal.Instance.getString(R.string.login_error_message))
                            .setPositiveButton("OK") { dialog, which ->
                                viewModel.dismissAlertDialog()
                                dialog.dismiss()
                            }
                        alertDialog.create()
                        alertDialog.show()
                    }
                }.collect()
            }
        }
    }
}