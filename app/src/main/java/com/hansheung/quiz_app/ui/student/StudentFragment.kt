package com.hansheung.quiz_app.ui.student

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hansheung.mob21firebase.ui.base.BaseFragment
import com.hansheung.mob21firebase.ui.base.BaseViewModel
import com.hansheung.quiz_app.R
import com.hansheung.quiz_app.databinding.FragmentStudentBinding
import com.hansheung.quiz_app.ui.teacher.TeacherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudentFragment(

) : BaseFragment() {

    private lateinit var binding: FragmentStudentBinding
    override val viewModel: StudentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun setupUiComponents(view: View) {
        super.setupUiComponents(view)

        val toolBarTitle = requireActivity().findViewById<TextView>(R.id.toolbarTitle)
        toolBarTitle.text = "Students"

        binding.btnSubmit.setOnClickListener {
            findNavController().navigate(
                StudentFragmentDirections.actionStudentFragmentToStudentQuizFragment(
                    binding.etQuizID.text.toString()
                )
            )
        }
    }

}