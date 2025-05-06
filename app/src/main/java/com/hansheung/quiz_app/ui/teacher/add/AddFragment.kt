package com.hansheung.quiz_app.ui.teacher.add

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hansheung.mob21firebase.ui.base.BaseFragment
import com.hansheung.quiz_app.R
import com.hansheung.quiz_app.ui.teacher.base.BaseAddEditFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddFragment : BaseAddEditFragment() {
    override val viewModel: AddViewModel by viewModels()

    override fun setupUiComponents(view: View) {
        super.setupUiComponents(view)
        binding.etChangeText.setText(R.string.add_quiz)
        binding.uploadCsvPart.visibility = View.VISIBLE

        lifecycleScope.launch {
            viewModel.finish.collect{
                findNavController().popBackStack()
            }
        }
    }
}