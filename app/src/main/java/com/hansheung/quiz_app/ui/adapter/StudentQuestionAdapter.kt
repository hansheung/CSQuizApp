package com.hansheung.quiz_app.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.hansheung.quiz_app.data.model.Question
import com.hansheung.quiz_app.databinding.ItemStudentQuizBinding

class StudentQuestionAdapter (
    private var questions: List<Question>,
): RecyclerView.Adapter<StudentQuestionAdapter.QuestionViewHolder>() {
    private var answerList = mutableListOf<String>()
    //private var role: Role? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val binding = ItemStudentQuizBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return QuestionViewHolder(binding)
    }
    override fun getItemCount(): Int = questions.size
    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val item = questions[position]
        holder.bind(item, position)


    }
//    fun setRole(role: Role) {
//        this.role = role
//    }
    fun setQuestion(questions: List<Question>) {
        this.questions = questions
        answerList = MutableList(questions.size) { "" }
        notifyDataSetChanged()
    }
    fun getAnswerList(): List<String> = answerList.toList()
    inner class QuestionViewHolder(
        private val binding: ItemStudentQuizBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(question: Question, position: Int) {
            binding.run {
                val buttons = setOf(rbAns1, rbAns2, rbAns3, rbAns4)
                tvTitleQuiz.text = question.questionText
                buttons.forEachIndexed { index, button ->
                    button.apply {
                        text = question.options[index]
                        isChecked = answerList[position] == question.options[index]
                        setOnClickListener { view ->
                            buttons.map {
                                if(it.id != view.id) it.isChecked = false
                            }
                            answerList[position] = (view as RadioButton).text.toString()
                        }
                    }
                }
            }
        }
    }

}