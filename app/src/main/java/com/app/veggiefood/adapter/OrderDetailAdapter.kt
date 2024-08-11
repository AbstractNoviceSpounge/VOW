package com.app.veggiefood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.veggiefood.activity.OrderDetailActivity
import com.app.veggiefood.databinding.AdpOrderdetailBinding
import com.app.veggiefood.model.response.HistoryModel
import com.bumptech.glide.Glide

class OrderDetailAdapter(
    val mContext: OrderDetailActivity,
    val orderModel: ArrayList<HistoryModel>
) :
    RecyclerView.Adapter<OrderDetailAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            AdpOrderdetailBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    }

    override fun getItemCount(): Int {
        return orderModel.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = orderModel[position]
        holder.binding.tvProductName.text = model.name
        holder.binding.tvPrice.text = "₹ " + model.price
        holder.binding.tvQuantity.text = "Quantity:- " + model.quantity

        Glide.with(mContext).load(model.image).into(holder.binding.ivProductImage)

    }

    class MyViewHolder(val binding: AdpOrderdetailBinding) : RecyclerView.ViewHolder(
        binding.root
    )

}