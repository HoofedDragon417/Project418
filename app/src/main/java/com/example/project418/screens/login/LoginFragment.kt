package com.example.project418.screens.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.project418.R
import com.example.project418.common.BaseFragment
import com.example.project418.databinding.FragmentLoginBinding
import com.example.project418.models.Department
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: LoginVM by viewModels()

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

        binding.etLogin.doAfterTextChanged {
            viewModel.onLoginChange()
        }

        binding.etPassword.doAfterTextChanged {
            viewModel.onPasswordChange()
        }

        binding.btnLogin.setOnClickListener {
            val userLogin = binding.etLogin.text.toString()
            val userPassword = binding.etPassword.text.toString()

            viewModel.authorization(Department(login = userLogin, password = userPassword))
        }
    }

    private fun subscription() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginError.onEach {
                    binding.loginContainer.error = it
                }.collect()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.passwordError.onEach {
                    binding.passwordContainer.error = it
                }.collect()
            }
        }
    }
}