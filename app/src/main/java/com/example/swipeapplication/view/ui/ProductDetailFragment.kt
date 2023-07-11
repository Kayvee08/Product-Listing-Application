package com.example.swipeapplication.view.ui

import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.swipeapplication.R
import com.example.swipeapplication.databinding.FragmentProductDetailBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class ProductDetailFragment : BottomSheetDialogFragment(){

    private lateinit var binding: FragmentProductDetailBinding
    private val args: ProductDetailFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val contextThemeWrapper = ContextThemeWrapper(activity, R.style.Base_Theme_SwipeApplication)
        val view = inflater.cloneInContext(contextThemeWrapper)
            .inflate(R.layout.fragment_product_detail, container, false)

        binding = FragmentProductDetailBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arguments = args
        setData(arguments)
    }

    private fun setData(arguments: ProductDetailFragmentArgs) {
        binding.apply {
            Glide.with(requireContext()).load(arguments.productDetails.image)
                .placeholder(R.drawable.no_image).into(productIv)
            productName.text = arguments.productDetails.productName ?: ""
            productType.text = arguments.productDetails.productType ?: ""
            productPrice.text = "Rs ${arguments.productDetails.price ?: ""}"
            productTax.text = "Tax ${arguments.productDetails.tax ?: ""}"
        }
    }

    override fun getTheme(): Int {
        return R.style.Theme_SwipeApplication_BottomSheetDialog
    }

}