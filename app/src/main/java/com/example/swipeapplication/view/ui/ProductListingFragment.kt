package com.example.swipeapplication.view.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.swipeapplication.data.ProductDetailsDto
import com.example.swipeapplication.view.viewmodel.ProductViewModel
import com.example.swipeapplication.R
import com.example.swipeapplication.databinding.FragmentProductListingBinding
import com.example.swipeapplication.view.adapter.ProductListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProductListingFragment : Fragment() {

    private lateinit var binding: FragmentProductListingBinding
    private val viewModel: ProductViewModel by viewModel()
    private lateinit var productAdapter: ProductListAdapter
    private var productList: ArrayList<ProductDetailsDto> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState?.containsKey("productList") == true){
            productList = savedInstanceState.getParcelableArrayList("productList")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductListingBinding.inflate(layoutInflater)
        productAdapter = ProductListAdapter(productList){item->
            findNavController().navigate(ProductListingFragmentDirections.actionProductListingFragmentToProductDetailFragment(item))

        }
        binding.productsRv.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = productAdapter
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.products.observe(viewLifecycleOwner) { remoteResponse ->
            if (remoteResponse.loading) showProgress(true) else showProgress(false)
            if (!remoteResponse.products.isNullOrEmpty()) {
                if(remoteResponse.products!!.isNotEmpty()) {
                    binding.productsCount.text = "${remoteResponse.products?.size.toString()} Products"
                    productList.clear()
                    productList.addAll(remoteResponse.products!!)
                    productAdapter.notifyDataSetChanged()
                } else{
                    binding.productsCount.text = "Some Error Occurred."
                }
            }
        }

        setListeners()

        viewModel.fetchProducts()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("productList", productList)
    }

    private fun showProgress(showProgress: Boolean) {
        if (showProgress) binding.progressCircular.visibility =
            View.VISIBLE else binding.progressCircular.visibility = View.GONE
    }

    private fun setListeners(){
        binding.addProduct.setOnClickListener {
            findNavController().navigate(R.id.action_productListingFragment_to_addProductFragment)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            ProductListingFragment().apply {
            }
    }
}