package com.hansheung.quiz_app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hansheung.quiz_app.data.model.Quiz
import com.hansheung.quiz_app.databinding.ItemQuizBinding

class QuizAdapter(

    private var quizzes: List<Quiz>

): RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemQuizBinding.inflate(inflater,parent, false)
        return QuizViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        val diet = quizzes[position]
        holder.bind(diet)
    }

    override fun getItemCount() = quizzes.size

    fun setQuizzes(quizzes: List<Quiz>){
        this.quizzes = quizzes
        notifyDataSetChanged()
    }

    inner class QuizViewHolder(
        private val binding: ItemQuizBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(quiz: Quiz){
            binding.run{
                tvTitle.text = quiz.title
                cvQuiz.setOnClickListener{ listener?.onClickItem(quiz) }
                ivDelete.setOnClickListener { listener?.onDeleteItem(quiz) }
                ivEdit.setOnClickListener {listener?.onClickEditItem(quiz)}
            }
        }
    }

    interface Listener {
        fun onClickItem(quiz: Quiz)
        fun onClickEditItem(quiz: Quiz)
        fun onDeleteItem(quiz: Quiz)
    }
}