package com.hansheung.mob21firebase.ui.base


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.hansheung.quiz_app.R
import kotlinx.coroutines.launch

abstract class BaseFragment: Fragment() {

    protected abstract val viewModel:BaseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onFragmentResult()
        setupUiComponents(view)
        setupViewModelObserver()
    }

    protected open fun onFragmentResult(){}

    protected open fun setupViewModelObserver(){
        lifecycleScope.launch {
            viewModel.error.collect{
                showError(requireView(), it)
            }
        }
    }

    protected open fun setupUiComponents(view: View){
        val toolBarLayout = requireActivity().findViewById<LinearLayout>(R.id.toolbarLayout)
        toolBarLayout.visibility = View.VISIBLE

        val tvLogout = requireActivity().findViewById<TextView>(R.id.tvLogout)
        tvLogout.setOnClickListener {
            viewModel.logout()

            findNavController().navigate(R.id.action_global_loginFragment)
        }
    }

    fun showError(view: View, msg: String){
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).apply{
            setBackgroundTint(
                ContextCompat.getColor(
                    requireContext(),
                    com.google.android.material.R.color.design_default_color_error
                )
            )
        }.show()
    }
}