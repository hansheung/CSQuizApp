package com.hansheung.quiz_app.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hansheung.mob21firebase.ui.base.BaseFragment
import com.hansheung.quiz_app.R
import com.hansheung.quiz_app.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    private lateinit var binding: FragmentLoginBinding
    override val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun setupUiComponents(view: View) {
        super.setupUiComponents(view)

        val toolBarLayout = requireActivity().findViewById<LinearLayout>(R.id.toolbarLayout)
        toolBarLayout.visibility = View.GONE

        binding.btnGoogleLogin.setOnClickListener {
            viewModel.loginWithGoogle(requireContext())
        }
    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

        lifecycleScope.launch {
            viewModel.shouldChooseRole.collect {
                findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToChooseRoleFragment()
                )
            }
        }

        lifecycleScope.launch {
            viewModel.navigateToRole.collect { role ->
                if(role == "teacher") {
                    findNavController().navigate(
                        LoginFragmentDirections.actionLoginFragmentToTeacherFragment()
                    )
                }

                if(role == "student") {
                    findNavController().navigate(
                        LoginFragmentDirections.actionLoginFragmentToStudentFragment()
                    )
                }
            }
        }

        lifecycleScope.launch {
            viewModel.error.collect{msg->
                showError(requireView(), msg)
            }
        }
    }
}