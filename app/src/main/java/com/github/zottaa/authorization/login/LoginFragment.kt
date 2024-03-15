package com.github.zottaa.authorization.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.github.zottaa.core.AbstractFragment
import com.github.zottaa.core.ProvideViewModel
import com.github.zottaa.databinding.FragmentLoginBinding
import com.google.android.material.textfield.TextInputEditText

class LoginFragment : AbstractFragment<FragmentLoginBinding>() {
    override fun bind(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentLoginBinding.inflate(inflater, container, false)

    private lateinit var viewModel: LoginViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as ProvideViewModel).viewModel(LoginViewModel::class.java)

        addListener(binding.loginEditText)
        addListener(binding.passwordEditText)

        binding.loginButton.setOnClickListener {
            viewModel.login(
                binding.loginEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }

        binding.goToRegistrationButton.setOnClickListener {
            viewModel.goToRegistration()
        }

        viewModel.init()

        if (savedInstanceState != null) {
            binding.loginEditText.setText(savedInstanceState.getString(LOGIN_KEY))
            binding.passwordEditText.setText(savedInstanceState.getString(PASSWORD_KEY))
        }
    }


    private fun addListener(editText: TextInputEditText) {
        editText.addTextChangedListener {
            binding.loginButton.isEnabled = binding.loginEditText.text.toString()
                .isNotEmpty() && binding.passwordEditText.text.toString().isNotEmpty()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LOGIN_KEY, binding.loginEditText.text.toString())
        outState.putString(PASSWORD_KEY, binding.passwordEditText.text.toString())
    }

    companion object {
        private const val LOGIN_KEY = "loginKey"
        private const val PASSWORD_KEY = "passwordKey"
    }
}