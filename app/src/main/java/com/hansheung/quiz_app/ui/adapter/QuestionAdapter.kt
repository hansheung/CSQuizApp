package com.hansheung.quiz_app.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hansheung.quiz_app.data.model.Question
import com.hansheung.quiz_app.databinding.ItemQuestionBinding
import javax.inject.Inject

class QuestionAdapter(
    private var questions: List<Question>
) : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {
    var listener: QuizAdapter.Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder =
        QuestionViewHolder(
            ItemQuestionBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )


    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.bind(questions[position])
    }

    override fun getItemCount(): Int = questions.size

    fun setQuestion(question: List<Question>) {
        this.questions = question
        notifyDataSetChanged()
    }


    inner class QuestionViewHolder(
        private val binding: ItemQuestionBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(question: Question) {
            binding.tvQuestion.text = question.questionText

            val options = listOf(binding.RadioA, binding.RadioB, binding.RadioC, binding.RadioD)
            options.forEachIndexed { index, radioButton ->
                radioButton.text = question.options.getOrNull(index) ?: ""
            }

            val correctIndex = question.options.indexOfFirst {
                Log.d("debugging", it)
                Log.d("debugging", question.correctAnswer)

                it == question.correctAnswer

            }
            Log.d("debugging", correctIndex.toString())



            if (correctIndex != -1) {
                options[correctIndex].isChecked = true
            } else {
                options.forEach { it.isChecked = false } // no match found, clear all
            }
        }
    }
}