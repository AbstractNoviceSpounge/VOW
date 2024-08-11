package com.app.veggiefood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.veggiefood.activity.OrderHistoryActivity
import com.app.veggiefood.databinding.AdpOrderItemBinding
import com.app.veggiefood.model.response.OrderModel

class OrderHistoryAdapter(
    val mContext: OrderHistoryActivity,
    val orderList: ArrayList<OrderModel>,
    val onclick:onItemClick
) : RecyclerView.Adapter<OrderHistoryAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            AdpOrderItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = orderList[position]
        holder.binding.tvOrderId.text = "Order Id:- " +model.order_id
        holder.binding.tvDate.text = "Date:- " +model.date
        holder.binding.tvPrice.text = "Price:- " +model.final_price

        holder.itemView.setOnClickListener {
            onclick.onClick(position, orderModel = orderList[position])
        }
    }

    class MyViewHolder(val binding: AdpOrderItemBinding) : RecyclerView.ViewHolder(
        binding.root
    )

    interface onItemClick{
        fun onClick(position: Int,orderModel: OrderModel)
    }
}