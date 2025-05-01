package com.hansheung.quiz_app.ui.teacher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hansheung.mob21firebase.ui.base.BaseFragment
import com.hansheung.mob21firebase.ui.base.BaseViewModel
import com.hansheung.quiz_app.R
import com.hansheung.quiz_app.data.model.Quiz
import com.hansheung.quiz_app.databinding.FragmentTeacherBinding
import com.hansheung.quiz_app.ui.ChooseRoleViewModel
import com.hansheung.quiz_app.ui.adapter.QuizAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TeacherFragment(

) : BaseFragment() {

    private lateinit var binding: FragmentTeacherBinding
    private lateinit var adapter: QuizAdapter

    override val viewModel: TeacherViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTeacherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUiComponents(view: View) {
        super.setupUiComponents(view)

        val toolBarTitle = requireActivity().findViewById<TextView>(R.id.toolbarTitle)
        toolBarTitle.text = "Teacher"

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setupAdapter()

        binding.fabAdd.setOnClickListener {
            findNavController().navigate(TeacherFragmentDirections.actionTeacherFragmentToAddFragment())
        }
    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()
        lifecycleScope.launch {
            viewModel.quizzes.collect { quizzes ->
                adapter.setQuizzes(quizzes)
            }
        }
    }

    private fun setupAdapter() {
        adapter = QuizAdapter(emptyList())
        binding.rvQuiz.adapter = adapter
        binding.rvQuiz.layoutManager = LinearLayoutManager(requireContext())
        adapter.listener = object: QuizAdapter.Listener {
            override fun onClickItem(quiz: Quiz) {
                findNavController().navigate(
                    TeacherFragmentDirections.actionTeacherFragmentToQuizFragment(
                        quiz.quizId!!
                    )
                )
            }

            override fun onClickEditItem(quiz: Quiz) {
                findNavController().navigate(
                    TeacherFragmentDirections.actionTeacherFragmentToEditFragment(
                        quiz.quizId!!
                    )
                )
            }
            override fun onDeleteItem(quiz: Quiz) { viewModel.deleteQuiz(quiz.quizId!!) }
        }

    }
}