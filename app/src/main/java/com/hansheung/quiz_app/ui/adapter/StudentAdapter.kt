package com.hansheung.quiz_app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hansheung.quiz_app.data.model.Student
import com.hansheung.quiz_app.databinding.ItemStudentBinding

class StudentAdapter(
    private var students: List<Student>

): RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    inner class StudentViewHolder(
        private val binding: ItemStudentBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(student: Student) {
            val email = "Student Email: ${student.studentEmail}"
            val score = "Score: ${student.score}"
            val timeTaken = "Time taken: ${student.timeTaken} seconds"
            binding.run {
                tvEmail.text = email
                tvScore.text = score
                tvTime.text = timeTaken
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = ItemStudentBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return StudentViewHolder(binding)
    }

    override fun getItemCount(): Int = students.size

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) = holder.bind(students[position])

    fun setStudents(students: List<Student>) {
        this.students = students
    }
}