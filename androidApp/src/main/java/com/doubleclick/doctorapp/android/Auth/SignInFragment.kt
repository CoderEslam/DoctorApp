package com.doubleclick.doctorapp.android.Auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.doubleclick.doctorapp.android.Home.HomeActivity
import com.doubleclick.doctorapp.android.Model.Auth.Login
import com.doubleclick.doctorapp.android.Model.Auth.LoginCallback
import com.doubleclick.doctorapp.android.Repository.remot.RepositoryRemot
import com.doubleclick.doctorapp.android.ViewModel.MainViewModel
import com.doubleclick.doctorapp.android.ViewModel.MainViewModelFactory
import com.doubleclick.doctorapp.android.databinding.FragmentSignInBinding
import com.doubleclick.doctorapp.android.utils.SessionManger.getCurrentPassword
import com.doubleclick.doctorapp.android.utils.SessionManger.getCurrentUserEmail
import com.doubleclick.doctorapp.android.utils.SessionManger.setImage
import com.doubleclick.doctorapp.android.utils.SessionManger.updateSession
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern


class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private lateinit var viewModel: MainViewModel
    private val regex = "[a-z0-9]+@[a-z]+\\.[a-z]{2,3}"
    private val TAG = "SignInFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(RepositoryRemot())
        )[MainViewModel::class.java]
        binding.signIn.setOnClickListener {
            signIn()
        }

        binding.forgetPassword.setOnClickListener {
            ForgetPasswordFragment().show(requireActivity().supportFragmentManager, "")
        }

        binding.dontHaveAccount.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment())
        }
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            if (requireActivity().getCurrentUserEmail()?.isNotEmpty() == true &&
                requireActivity().getCurrentPassword()?.isNotEmpty() == true
            ) {
                binding.emailSignIn.setText(requireActivity().getCurrentUserEmail().toString())
                binding.passwordSignIn.setText(requireActivity().getCurrentPassword().toString())
            }
        }
    }

    private fun signIn() {
        binding.animationView.visibility = View.VISIBLE
        if (isEmpty()) {
            if (isEmail()) {
                viewModel.getLoginResponse(
                    Login(
                        email = binding.emailSignIn.text.toString().trim(),
                        phone = "",
                        password = binding.passwordSignIn.text.toString().trim()
                    )
                ).observe(viewLifecycleOwner) {
                    it.clone().enqueue(object : Callback<LoginCallback> {
                        override fun onResponse(
                            call: Call<LoginCallback>,
                            response: Response<LoginCallback>
                        ) {
                            viewLifecycleOwner.lifecycleScope.launch {
                                response.body()?.let { user ->
                                    requireActivity().updateSession(
                                        user.user?.device_token.toString(),
                                        user.user?.email.toString(),
                                        binding.passwordSignIn.text.toString().trim(),
                                        user.user?.id.toString(),
                                        user.user?.name.toString(),
                                        user.user?.phone.toString(),
                                        user.role.toString()
                                    )
                                    requireActivity().setImage("${user.user?.name}_${user.user?.id}.jpg")
                                    startActivity(
                                        Intent(
                                            requireActivity(),
                                            HomeActivity::class.java
                                        )
                                    )
                                    Log.e(TAG, "onResponse: ${user.user}")
                                }
                            }
                        }

                        override fun onFailure(call: Call<LoginCallback>, t: Throwable) {
                            Log.e(TAG, "onResponse: ${t.message}")
                        }

                    })
                }
            } else {
                viewModel.getLoginResponse(
                    Login(
                        email = "",
                        phone = binding.emailSignIn.text.toString().trim(),
                        password = binding.passwordSignIn.text.toString().trim()
                    )
                ).observe(viewLifecycleOwner) {
                    it.clone().enqueue(object : Callback<LoginCallback> {
                        override fun onResponse(
                            call: Call<LoginCallback>,
                            response: Response<LoginCallback>
                        ) {
                            viewLifecycleOwner.lifecycleScope.launch {
                                response.body()?.let { user ->
                                    requireActivity().updateSession(
                                        user.user?.device_token.toString(),
                                        user.user?.phone.toString(),
                                        binding.passwordSignIn.text.toString().trim(),
                                        user.user?.id.toString(),
                                        user.user?.name.toString(),
                                        user.user?.phone.toString(),
                                        user.role.toString()
                                    )
                                    startActivity(
                                        Intent(
                                            requireActivity(),
                                            HomeActivity::class.java
                                        )
                                    )
                                }
                            }
                        }

                        override fun onFailure(call: Call<LoginCallback>, t: Throwable) {
                            Log.e(TAG, "onResponse: ${t.message}")
                        }

                    })
                }
            }

        } else {
            Snackbar.make(binding.root, "fill failed", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun isEmpty(): Boolean =
        binding.emailSignIn.text.toString().isNotEmpty() &&
                binding.passwordSignIn.text.toString().isNotEmpty()


    private fun isEmail(): Boolean =
        Pattern.compile(regex)
            .matcher(binding.emailSignIn.text.toString())
            .matches()


}

