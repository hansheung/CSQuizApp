package com.hansheung.quiz_app.ui

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hansheung.mob21firebase.ui.base.BaseFragment
import com.hansheung.quiz_app.R
import com.hansheung.quiz_app.databinding.FragmentChooseRoleBinding
import com.hansheung.quiz_app.ui.login.LoginFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChooseRoleFragment : BaseFragment() {

    override val viewModel:ChooseRoleViewModel by viewModels()
    private lateinit var binding: FragmentChooseRoleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChooseRoleBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun setupUiComponents(view: View) {
        super.setupUiComponents(view)

        val toolBarTitle = requireActivity().findViewById<TextView>(R.id.toolbarTitle)
        toolBarTitle.text = "Choose Role"

        binding.btnTeacher.setOnClickListener {
            viewModel.saveUserRole("teacher")
        }

        binding.btnStudent.setOnClickListener {
            viewModel.saveUserRole("student")
        }
    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

        lifecycleScope.launch {
            viewModel.roleSaved.collect { role ->
                if(role == "teacher") {
                    findNavController().navigate(
                        ChooseRoleFragmentDirections.actionChooseRoleFragmentToTeacherFragment()
                    )
                }

                if(role == "student") {
                    findNavController().navigate(
                        ChooseRoleFragmentDirections.actionChooseRoleFragmentToStudentFragment()
                    )
                }
            }
        }
    }
}