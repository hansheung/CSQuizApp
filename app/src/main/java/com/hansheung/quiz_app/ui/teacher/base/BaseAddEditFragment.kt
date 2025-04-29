package com.hansheung.quiz_app.ui.teacher.base

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.hansheung.mob21firebase.ui.base.BaseFragment
import com.hansheung.quiz_app.databinding.FragmentAddEditBinding


abstract class BaseAddEditFragment : BaseFragment() {
    protected lateinit var binding: FragmentAddEditBinding
    abstract override val viewModel: BaseAddEditViewModel
    private var selectedCsvFile: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedCsvFile = it
            binding.tvSelectedFile.text = it.lastPathSegment
            viewModel.getQuestionsFromCSV(it)
        }
    }

    override fun setupUiComponents(view: View) {
        super.setupUiComponents(view)
        binding.run {
            btnUploadCsv.setOnClickListener {
                openFilePicker()
            }
            btnSubmit.setOnClickListener {
                viewModel.submit(
                    tvTitle.text.toString(),
                    tvDesc.text.toString(),
                    tvTimeLimit.text.toString(),
                    selectedCsvFile
                )
            }
        }
    }

    private fun openFilePicker() {
        getContent.launch("text/csv")
    }
}



