package com.hansheung.quiz_app.core.service

import android.content.Context
import android.net.Uri
import com.hansheung.quiz_app.data.model.Question
import java.io.BufferedReader
import java.io.InputStreamReader

class ProcessCSV (
    private val context: Context
) {
    fun getQuestionsFromCSV(uri: Uri): List<Question> =
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            val inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            extractContent(bufferedReader)
        } ?: emptyList()

    private fun extractContent(bufferedReader: BufferedReader): List<Question> {
        val questions: MutableList<Question> = mutableListOf()
        var isFirstLine = true
        bufferedReader.forEachLine { line ->
            if(isFirstLine) isFirstLine = false
            else {
                val values = line.split(",")
                questions.add(
                    Question(questionText = values[0], options = values.subList(1, 5), correctAnswer = values[5])
                )
            }
        }
        return questions.toList()
    }
}