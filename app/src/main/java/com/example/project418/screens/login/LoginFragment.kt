package com.example.project418.screens.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.project418.databinding.FragmentLoginBinding
<<<<<<< HEAD
import com.example.project418.models.Department
=======
>>>>>>> origin/master
import com.github.terrakok.cicerone.Router

class LoginFragment(private val router: Router) : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: LoginVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.setRouter(router)
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
<<<<<<< HEAD
            val userLogin = binding.etLogin.text.toString()
            val userPassword = binding.etPassword.text.toString()

            viewModel.auth(Department(login = userLogin, password = userPassword), requireContext())
=======

>>>>>>> origin/master
        }
    }
}
