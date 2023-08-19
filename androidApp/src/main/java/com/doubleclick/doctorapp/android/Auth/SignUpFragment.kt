package com.doubleclick.doctorapp.android.Auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.doubleclick.doctorapp.android.Home.HomeActivity
import com.doubleclick.doctorapp.android.Model.Auth.Registration
import com.doubleclick.doctorapp.android.Model.Auth.ResopnsLogin
import com.doubleclick.doctorapp.android.Repository.remot.RepositoryRemot
import com.doubleclick.doctorapp.android.ViewModel.MainViewModel
import com.doubleclick.doctorapp.android.ViewModel.MainViewModelFactory
import com.doubleclick.doctorapp.android.databinding.FragmentSignUpBinding
import com.doubleclick.doctorapp.android.utils.SessionManger.setImage
import com.doubleclick.doctorapp.android.utils.SessionManger.updateSession
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern


class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModel: MainViewModel
    private val regex = "^(.+)@(.+)$"
    private val TAG = "SignUpFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(RepositoryRemot())
        )[MainViewModel::class.java]
        binding.IHaveAnAccount.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.signUp.setOnClickListener {
            signUp()
        }

    }


    private fun signUp() {
        binding.animationView.visibility = View.VISIBLE
        if (isEmpty()) {
            if (isEmail()) {
                viewModel.getRegisterResponse(
                    Registration(
                        name = binding.nameSignUp.text.toString().trim(),
                        email = binding.emailSignUp.text.toString().trim(),
                        phone = "",
                        password = binding.passwordSignUp.text.toString().trim(),
                        password_confirmation = binding.passwordSignUp.text.toString().trim()
                    )
                ).observe(viewLifecycleOwner) {
                    it.clone().enqueue(object : Callback<ResopnsLogin> {
                        override fun onResponse(
                            call: Call<ResopnsLogin>,
                            response: Response<ResopnsLogin>
                        ) {
                            viewLifecycleOwner.lifecycleScope.launch {
                                response.body()?.let { user ->
                                    requireActivity().updateSession(
                                        user.user.device_token!!,
                                        user.user.email!!,
                                        binding.passwordSignUp.text.toString().trim(),
                                        user.user.id.toString(),
                                        user.user.name!!,
                                        user.user.phone!!
                                    )
                                    requireActivity().setImage("${user.user.name}_${user.user.id}.jpg")
                                    startActivity(
                                        Intent(
                                            requireActivity(),
                                            HomeActivity::class.java
                                        )
                                    )
                                }
                            }
                        }

                        override fun onFailure(call: Call<ResopnsLogin>, t: Throwable) {
                            Log.e(TAG, "onFailure: ${t.message}")
                        }

                    })
                }
            } else {
                viewModel.getRegisterResponse(
                    Registration(
                        name = binding.nameSignUp.text.toString().trim(),
                        email = "",
                        phone = binding.emailSignUp.text.toString().trim(),
                        password = binding.passwordSignUp.text.toString().trim(),
                        password_confirmation = binding.passwordSignUp.text.toString().trim()
                    )
                ).observe(viewLifecycleOwner) {
                    it.clone().enqueue(object : Callback<ResopnsLogin> {
                        override fun onResponse(
                            call: Call<ResopnsLogin>,
                            response: Response<ResopnsLogin>
                        ) {
                            viewLifecycleOwner.lifecycleScope.launch {
                                response.body()?.let { user ->
                                    requireActivity().updateSession(
                                        user.user.device_token!!,
                                        user.user.phone!!,
                                        binding.passwordSignUp.text.toString().trim(),
                                        user.user.id.toString(),
                                        user.user.name!!,
                                        user.user.phone!!
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

                        override fun onFailure(call: Call<ResopnsLogin>, t: Throwable) {
                            Log.e(TAG, "onFailure: ${t.message}")
                        }

                    })
                }
            }

        } else {
            Snackbar.make(binding.root, "fill failed", Snackbar.LENGTH_SHORT).show()
        }
    }


    private fun isEmpty(): Boolean =
        binding.emailSignUp.text.toString().isNotEmpty() &&
                binding.passwordSignUp.text.toString().isNotEmpty()


    private fun isEmail(): Boolean =
        Pattern.compile(regex).matcher(binding.emailSignUp.text.toString())
            .matches()


}