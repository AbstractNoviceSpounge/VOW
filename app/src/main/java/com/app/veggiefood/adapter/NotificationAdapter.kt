package com.app.veggiefood.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.veggiefood.activity.NotificationActivity
import com.app.veggiefood.databinding.AdpCartitemBinding
import com.app.veggiefood.databinding.AdpNotificationBinding
import com.app.veggiefood.model.response.NotificationModel

class NotificationAdapter(
    val mContext: NotificationActivity,
    val notificationModel: ArrayList<NotificationModel>
) : RecyclerView.Adapter<NotificationAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            AdpNotificationBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    }

    override fun getItemCount(): Int {
        return notificationModel.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = notificationModel[position]
        holder.binding.tvTitle.text = model.message
        holder.binding.tvDate.text = "Date:- " + model.created_at
    }

    class MyViewHolder(val binding: AdpNotificationBinding) : RecyclerView.ViewHolder(
        binding.root
    )
}