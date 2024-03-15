package com.github.zottaa.authorization.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.github.zottaa.authorization.login.LoginFragment
import com.github.zottaa.core.AbstractFragment
import com.github.zottaa.core.ProvideViewModel
import com.github.zottaa.databinding.FragmentRegistrationBinding
import com.google.android.material.textfield.TextInputEditText

class RegistrationFragment : AbstractFragment<FragmentRegistrationBinding>() {
    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) =
        FragmentRegistrationBinding.inflate(inflater, container, false)

    private lateinit var viewModel: RegistrationViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as ProvideViewModel).viewModel(RegistrationViewModel::class.java)

        addListener(binding.registrationLoginEditText)
        addListener(binding.registrationPasswordEditText)

        binding.registrationButton.setOnClickListener {
            viewModel.registration(
                binding.registrationLoginEditText.text.toString(),
                binding.registrationPasswordEditText.text.toString()
            )
            hideKeyboard()
        }

        binding.goToLoginButton.setOnClickListener {
            viewModel.comeback()
        }

        if (savedInstanceState != null) {
            binding.registrationLoginEditText.setText(savedInstanceState.getString(LOGIN_KEY))
            binding.registrationPasswordEditText.setText(savedInstanceState.getString(PASSWORD_KEY))
        }
    }

    private fun addListener(editText: TextInputEditText) {
        editText.addTextChangedListener {
            binding.registrationButton.isEnabled = binding.registrationLoginEditText.text.toString()
                .isNotEmpty() && binding.registrationPasswordEditText.text.toString().isNotEmpty()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LOGIN_KEY, binding.registrationLoginEditText.text.toString())
        outState.putString(PASSWORD_KEY, binding.registrationPasswordEditText.text.toString())
    }

    companion object {
        private const val LOGIN_KEY = "loginKey"
        private const val PASSWORD_KEY = "passwordKey"
    }
}