package com.app.veggiefood.adapter

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.veggiefood.R
import com.app.veggiefood.activity.MainActivity
import com.app.veggiefood.databinding.AdpProductsBinding
import com.app.veggiefood.model.response.ProductModel
import com.app.veggiefood.util.StaticData
import com.bumptech.glide.Glide

class ProductAdapter(val mContext: MainActivity, val productModelList: ArrayList<ProductModel>,
    val onclick:onItemClick) :
    RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            AdpProductsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    }

    override fun getItemCount(): Int {
        return productModelList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = productModelList[position]
        holder.binding.pName.text = model.name
        holder.binding.pPrice.text = "Discount Price:- ₹" + model.discounted_price
        holder.binding.pSPrice.text = "Price:- ₹" + model.base_price
        holder.binding.tvDesc.text = model.short_description
        holder.binding.pSPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

        if (model.image.size>0){
            Glide.with(holder.itemView.context).load(model.image[0])
                .placeholder(R.drawable.food_icon)
                .into(holder.binding.pImg)

        }

        holder.itemView.setOnClickListener {
           onclick.onClick(position,productModelList[position])
        }
    }

    class MyViewHolder(val binding: AdpProductsBinding) : RecyclerView.ViewHolder(
        binding.root
    )

    interface onItemClick{
        fun onClick(position: Int,model:ProductModel)
    }
}