package com.example.swipeapplication.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.swipeapplication.data.ProductDetailsDto
import com.example.swipeapplication.R
import com.example.swipeapplication.databinding.ItemProductBinding

class ProductListAdapter(
    private var productList: List<ProductDetailsDto>,
    var itemClicked: (ProductDetailsDto) -> Unit
) : RecyclerView.Adapter<ProductListAdapter.ProductListVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListVH {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductListVH(binding)
    }

    override fun getItemCount() = productList.size

    override fun onBindViewHolder(holder: ProductListVH, position: Int) {
        holder.bind(productList[position])
    }

    inner class ProductListVH(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductDetailsDto) {
            binding.apply {
                productName.text = item.productName ?: ""
                productPrice.text = "Rs ${item.price ?: ""}"
                Glide.with(itemView.context).load(item.image).placeholder(R.drawable.no_image)
                    .into(productIv)
                root.setOnClickListener {
                    itemClicked(item)
                }
            }
        }
    }

}