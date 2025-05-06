package com.hansheung.quiz_app.ui.teacher.edit

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hansheung.quiz_app.R
import com.hansheung.quiz_app.ui.teacher.base.BaseAddEditFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditFragment : BaseAddEditFragment() {
    override val viewModel: EditViewModel by viewModels()

    override fun setupUiComponents(view: View) {
        super.setupUiComponents(view)
        binding.etChangeText.setText(R.string.edit_quiz)
        binding.uploadCsvPart.visibility = View.GONE

        lifecycleScope.launch {
            viewModel.quiz.collect { quiz ->
                quiz.let {
                    binding.run {
                        tvTitle.setText(it.title)
                        tvDesc.setText(it.desc)
                        tvTimeLimit.setText(it.timeLimit)
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.finish.collect{
                findNavController().popBackStack()
            }
        }
    }
}