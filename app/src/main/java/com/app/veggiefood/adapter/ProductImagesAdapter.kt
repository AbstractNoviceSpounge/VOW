package com.app.veggiefood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.veggiefood.R
import com.app.veggiefood.databinding.ImageItemBinding
import com.bumptech.glide.Glide

class ProductImagesAdapter(private val imageUrlList: List<String>) :
    RecyclerView.Adapter<ProductImagesAdapter.ViewPagerViewHolder>() {

    inner class ViewPagerViewHolder(val binding: ImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(imageUrl: String) {

            Glide.with(binding.root.context)
                .load(imageUrl)
                .into(binding.ivImage)
        }

    }

    override fun getItemCount(): Int = imageUrlList.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {

        val binding = ImageItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewPagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {

        holder.setData(imageUrlList[position])
    }

}