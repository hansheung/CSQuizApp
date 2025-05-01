package com.hansheung.quiz_app.ui.student.studentQuiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.hansheung.mob21firebase.core.service.AuthService
import com.hansheung.mob21firebase.ui.base.BaseViewModel
import com.hansheung.quiz_app.data.model.Quiz
import com.hansheung.quiz_app.data.model.Student
import com.hansheung.quiz_app.data.repo.QuizRepo
import com.hansheung.quiz_app.data.repo.UsersRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentQuizViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val quizRepo: QuizRepo,
    authService: AuthService
): BaseViewModel(authService) {

    private var currentQuiz: Quiz? = null
    protected val _quiz = MutableSharedFlow<Quiz>()
    val quiz: SharedFlow<Quiz> = _quiz

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val id = savedStateHandle.get<String>("studentQuizId").toString()
            showQuiz(id)
        }
    }
    private fun showQuiz(id: String) {
        viewModelScope.launch (Dispatchers.IO){
            errorHandler {
                    if(id.isEmpty()) throw Exception("Failed to get quiz, please check again.")
                val quiz = quizRepo.getQuizById(id) ?: throw Exception("Failed to get quiz, please check again.")
                currentQuiz = quiz
                _quiz.emit(quiz)

            }
        }
    }
    fun updateQuiz(score: Int, timeTaken: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = authService.getLoggedInUser()
            if(user != null) {
                val student = Student(user.email, score, timeTaken)
                val studentList = currentQuiz!!.studentList.toMutableList()
                val idx = studentList.indexOfFirst { it.studentEmail == student.studentEmail }
                if (idx != -1) {
                    studentList[idx] = student
                } else {
                    studentList.add(student)
                }
                errorHandler {
                    quizRepo.updateQuiz(currentQuiz!!.copy(studentList = studentList))
                }
            }
        }
    }

    fun getSeconds(timeLimit: String): Int {
        return timeLimit.toIntOrNull() ?: 0
    }

    fun getScore(list: List<String>): Int {
        val results = mutableListOf<Boolean>()
        val questions = currentQuiz!!.questions
        questions.forEachIndexed { index, _ ->
            results.add(questions[index].correctAnswer == list[index])
        }
        return results.count { it }
    }

    fun getCurrentQuiz(): Quiz? = currentQuiz
}