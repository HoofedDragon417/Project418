package com.example.project418.screens.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.project418.R
import com.example.project418.common.BaseFragment
import com.example.project418.databinding.FragmentLoginBinding
import com.example.project418.models.Department
import com.github.terrakok.cicerone.Router

class LoginFragment : BaseFragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: LoginVM by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        router?.let(viewModel::setRouter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            val userLogin = binding.etLogin.text.toString()
            val userPassword = binding.etPassword.text.toString()

            viewModel.auth(Department(login = userLogin, password = userPassword), requireContext())
        }
    }
}