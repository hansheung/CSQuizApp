package com.hansheung.quiz_app.ui.teacher.quiz

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hansheung.mob21firebase.ui.base.BaseFragment
import com.hansheung.quiz_app.data.model.Quiz
import com.hansheung.quiz_app.databinding.FragmentQuizBinding
import com.hansheung.quiz_app.ui.adapter.QuestionAdapter
import com.hansheung.quiz_app.ui.adapter.StudentAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuizFragment(

) : BaseFragment() {
    override val viewModel: QuizViewModel by viewModels()
    private lateinit var binding: FragmentQuizBinding
    lateinit var adapter: StudentAdapter
    private lateinit var questionAdapter: QuestionAdapter
    private lateinit var clipboardManager: ClipboardManager
    private var currentView: String = "Questions"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUiComponents(view: View) {
        super.setupUiComponents(view)
        setupAdapters()

        clipboardManager = requireContext().getSystemService(
            Context.CLIPBOARD_SERVICE
        ) as ClipboardManager

        binding.btnSwitch.setOnClickListener {
            currentView = if(currentView == "Questions") "Students" else "Questions"

            switchView()
        }


    }
    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

        clipboardManager = requireContext().getSystemService(
            Context.CLIPBOARD_SERVICE
        ) as ClipboardManager

        binding.rvQuestion.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = questionAdapter
        }

        lifecycleScope.launch {
            viewModel.quiz.collect {
                questionAdapter.setQuestion(it.questions)
                adapter.setStudents(it.studentList)
                viewQuiz(it)
            }
        }
    }

    private fun viewQuiz(quiz: Quiz){
        binding.run {
            etTitle.text = quiz.title
            etDesc.text = quiz.desc
            etTimeLimit.text = quiz.timeLimit
            btnCopyID.setOnClickListener {
                copyQuizId(quiz.quizId!!)
            }
        }
    }

    private fun copyQuizId(id: String) {
        val clipData = ClipData.newPlainText("CLIP_ID", id)
        clipboardManager.setPrimaryClip(clipData)
    }

    private fun setupAdapters() {
        questionAdapter = QuestionAdapter(emptyList())
        adapter = StudentAdapter(emptyList())
        binding?.run {
            rvQuestion.adapter = questionAdapter
            rvQuestion.layoutManager = LinearLayoutManager(requireContext())
            rvStudent.adapter = adapter
            rvStudent.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun switchView() {
        binding.run {
            when(currentView) {
                "Students" -> {
                    btnSwitch.text = "Students"
                    rvQuestion.visibility = View.GONE
                    rvStudent.visibility = View.VISIBLE
                }
                else -> {
                    btnSwitch.text = "Questions"
                    rvQuestion.visibility = View.VISIBLE
                    rvStudent.visibility = View.GONE
                }
            }
        }
    }
}