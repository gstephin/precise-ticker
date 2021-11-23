package com.tickerapp.ui.login

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.tickerapp.R
import com.tickerapp.database.RegisterDatabase
import com.tickerapp.database.RegisterRepository
import com.tickerapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentLoginBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login, container, false
        )

        val application = requireNotNull(this.activity).application

        val dao = RegisterDatabase.getInstance(application).registerDatabaseDao

        val repository = RegisterRepository(dao)

        val factory = LoginViewModelFactory(repository, application)

        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        binding.myLoginViewModel = loginViewModel

        binding.lifecycleOwner = this

        val preferences = activity?.getSharedPreferences("login", Context.MODE_PRIVATE);

        loginViewModel.navigatetoRegister.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished == true) {
                displayUsersList()
                loginViewModel.doneNavigatingRegiter()
            }
        })

        if (preferences!!.contains("isUserLogin"))
            navigateUserDetails()


        loginViewModel.errotoast.observe(viewLifecycleOwner, { hasError ->
            if (hasError == true) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT)
                    .show()
                loginViewModel.donetoast()
            }
        })

        loginViewModel.errotoastUsername.observe(viewLifecycleOwner, { hasError ->
            if (hasError == true) {
                Toast.makeText(
                    requireContext(),
                    "User doesnt exist,please Register!",
                    Toast.LENGTH_SHORT
                ).show()
                loginViewModel.donetoastErrorUsername()
            }
        })

        loginViewModel.errorToastInvalidPassword.observe(viewLifecycleOwner, { hasError ->
            if (hasError == true) {
                Toast.makeText(requireContext(), "Please check your Password", Toast.LENGTH_SHORT)
                    .show()
                loginViewModel.donetoastInvalidPassword()
            }
        })

        loginViewModel.navigatetoUserDetails.observe(viewLifecycleOwner, { hasFinished ->
            if (hasFinished == true) {
                Log.i("MYTAG", "insidi observe")
                navigateUserDetails()
                loginViewModel.doneNavigatingUserDetails()
            }
        })


        return binding.root
    }


    private fun displayUsersList() {
        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        NavHostFragment.findNavController(this).navigate(action)

    }

    private fun navigateUserDetails() {

        saveLogin()
        val action = LoginFragmentDirections.actionLoginFragmentToTickerFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }

    private fun saveLogin() {
        val preferences = requireActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        val editor = preferences.edit()
        editor.putBoolean("isUserLogin", true)
        editor.apply()
    }
}