package com.app.veggiefood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.veggiefood.activity.MyCartActivity
import com.app.veggiefood.databinding.AdpCartitemBinding
import com.app.veggiefood.databinding.AdpProductsBinding
import com.app.veggiefood.model.response.CartModel
import com.bumptech.glide.Glide

class MyCartAdapter(
    val mContext: MyCartActivity, val cartList: ArrayList<CartModel>,
    val onClick: onItemClick
) :
    RecyclerView.Adapter<MyCartAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            AdpCartitemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = cartList[position]
        holder.binding.tvProductName.text = model.product.name
        holder.binding.tvPrice.text = "₹ " + model.price
        holder.binding.tvOptions.text = model.variation.variation
        holder.binding.tvQuantity.text = "Quantity:- " + model.qty

        Glide.with(mContext).load(model.product.image).into(holder.binding.ivProductImage)

        holder.binding.ivEdit.setOnClickListener {
            onClick.onEditClick(position, cartModel = cartList[position])
        }
        holder.binding.ivDelete.setOnClickListener {
            onClick.onDeleteClick(position, cartModel = cartList[position])
        }
    }

    class MyViewHolder(val binding: AdpCartitemBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {

    }

    interface onItemClick {
        fun onEditClick(position: Int, cartModel: CartModel)
        fun onDeleteClick(position: Int, cartModel: CartModel)
    }


}