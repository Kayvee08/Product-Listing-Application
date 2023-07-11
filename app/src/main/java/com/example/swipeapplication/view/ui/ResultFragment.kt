package com.example.swipeapplication.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.swipeapplication.R
import com.example.swipeapplication.data.Status
import com.example.swipeapplication.databinding.FragmentResultBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ResultFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentResultBinding
    private val args: ResultFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentResultBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        args.let { data ->
            when (data.result.status) {
                Status.SUCCESS -> {
                    binding.statusIcon.setImageResource(R.drawable.ic_success)
                    binding.okBtn.text = "Yay"
                }

                Status.ERROR -> {
                    binding.statusIcon.setImageResource(R.drawable.ic_error)
                    binding.okBtn.text = "Please Try Later"
                }

                else -> {

                }
            }
            binding.message.text = data.result.message
        }
    }

    private fun setListeners() {
        binding.okBtn.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_productListingFragment)
        }
    }
}