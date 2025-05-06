package com.hansheung.quiz_app.ui.student.studentQuiz

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hansheung.mob21firebase.ui.base.BaseFragment
import com.hansheung.quiz_app.R
import com.hansheung.quiz_app.data.model.Quiz
import com.hansheung.quiz_app.databinding.FragmentStudentQuizBinding
import com.hansheung.quiz_app.ui.adapter.StudentQuestionAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StudentQuizFragment : BaseFragment() {
    private lateinit var binding: FragmentStudentQuizBinding
    private lateinit var adapter: StudentQuestionAdapter
    override val viewModel: StudentQuizViewModel by viewModels()
    private var timer: CountDownTimer? = null
    private var timeInMillis: Long? = null
    private var totalSeconds: Int? = null
    private val answers = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudentQuizBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun setupUiComponents(view: View) {
        super.setupUiComponents(view)
        setupQuesAdapter()
    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()
        lifecycleScope.launch {
            viewModel.quiz.collect {
                if (it != null) {
                    adapter.setQuestion(it.questions)
                    setupQuiz(it)
                    // Only start the timer if timeInMillis is set
                    if (timeInMillis != null) {
                        startTimer()
                    }
                }
            }
        }
    }
    private fun startTimer() {
        timer = object: CountDownTimer(timeInMillis!!, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeInMillis = millisUntilFinished
                updateTimer()
            }
            override fun onFinish() {
                endQuiz()
            }
        }
        timer?.start()
    }
    private fun updateTimer() {
        timeInMillis?.let {
            val seconds = (it / 1000L).toInt()
            binding.tvViewTimer.text = requireContext().getString(
                R.string.timer_display, seconds.toString()
            )
        }
    }
    private fun endQuiz() {
        timer?.cancel()
        viewModel.getCurrentQuiz()?.let {
            val score = viewModel.getScore(adapter.getAnswerList())
            val timeTaken = viewModel.getSeconds(it.timeLimit) - (timeInMillis!! / 1000L).toInt()
            viewModel.updateQuiz(score, timeTaken)
            lifecycleScope.launch {
                findNavController().navigate(
                    StudentQuizFragmentDirections.actionStudentQuizFragmentToScoreFragment(
                        score,
                        timeTaken
                    )
                )
            }
        }
    }

    private fun setupQuiz(quiz: Quiz) {
        binding.run {
            tvViewTitle.text = quiz.title
            tvViewDesc.text = quiz.desc
            totalSeconds = viewModel.getSeconds(quiz.timeLimit)
            timeInMillis = totalSeconds!! * 1000L
            binding.tvViewTimer.text = totalSeconds.toString()
            quiz.questions.forEach { answers.add(it.correctAnswer) }
            btnEndQuiz.setOnClickListener { endQuiz() }
        }
    }
    private fun setupQuesAdapter() {
        adapter = StudentQuestionAdapter(emptyList())
        binding.rvQuesList.adapter = adapter
        binding.rvQuesList.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        timer?.cancel()
        super.onDestroyView()
    }
}