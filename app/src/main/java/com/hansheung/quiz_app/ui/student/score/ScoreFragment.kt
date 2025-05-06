package com.hansheung.quiz_app.ui.student.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hansheung.mob21firebase.ui.base.BaseFragment
import com.hansheung.quiz_app.R
import com.hansheung.quiz_app.databinding.FragmentScoreBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScoreFragment : BaseFragment() {

    override val viewModel: ScoreViewModel by viewModels()
    //override fun getLayoutResource(): Int = R.layout.fragment_score
    private lateinit var binding: FragmentScoreBinding
    private val args: ScoreFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScoreBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun setupUiComponents(view: View) {
        super.setupUiComponents(view)
        binding?.run {
            tvScore.text = getString(R.string.score_text, args.scoreId)
            tvTimeTaken.text = getString(R.string.time_taken_text, args.timeTakenId)

            btnBack.setOnClickListener {
                findNavController().navigate(
                    R.id.studentFragment,
                    null,
                    NavOptions.Builder()
                        .setPopUpTo(R.id.scoreFragment, true)
                        .build()
                )
            }
        }
    }
}


