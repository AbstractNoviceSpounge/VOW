package com.app.veggiefood.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.veggiefood.R
import com.app.veggiefood.adapter.NotificationAdapter
import com.app.veggiefood.databinding.ActivityNotificationBinding
import com.app.veggiefood.extensions.isNetworkAvailable
import com.app.veggiefood.model.request.NotificationRequest
import com.app.veggiefood.model.response.NotificationModel
import com.app.veggiefood.util.PrefManager
import com.app.veggiefood.util.StaticData
import com.app.veggiefood.viewmodel.NotificationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationActivity : AppCompatActivity() {

    lateinit var binding: ActivityNotificationBinding
    lateinit var mContext: NotificationActivity
    private val viewModel: NotificationViewModel by viewModels()
    private var notificationModel: ArrayList<NotificationModel> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification)
        mContext = this
        initUI()
        addListner()
        addObserves()
    }

    private fun initUI() {
        if (isNetworkAvailable(mContext)) {
            viewModel.getNotifications(
                NotificationRequest(
                    user_id = PrefManager(mContext).getvalue(StaticData.id).toString()
                )
            )
        } else {
            Toast.makeText(
                mContext, getString(R.string.str_error_internet_connections),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun addListner() {
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun addObserves() {
        viewModel.isLoading.observe(this, Observer {
            if (it) {
                binding.pbLoadData.visibility = View.VISIBLE
            } else {
                binding.pbLoadData.visibility = View.GONE
            }
        })

        viewModel.isNotificationLiveData.observe(this, Observer {
            if (it.status == "success") {
                notificationModel.clear()
                notificationModel.addAll(it.data)
                if (notificationModel.size > 0) {
                    binding.rvNotifications.visibility = View.VISIBLE
                    binding.tvNoDataFound.visibility = View.GONE
                    setAdapter(notificationModel)
                } else {
                    binding.rvNotifications.visibility = View.GONE
                    binding.tvNoDataFound.visibility = View.VISIBLE
                }

            } else {
                binding.tvNoDataFound.visibility = View.VISIBLE
                binding.rvNotifications.visibility = View.GONE
            }
        })
    }

    private fun setAdapter(notificationModel: ArrayList<NotificationModel>) {
        binding.apply {
            rvNotifications.layoutManager = LinearLayoutManager(mContext)
            rvNotifications.setHasFixedSize(true)
            adapter = NotificationAdapter(mContext, notificationModel)
            rvNotifications.adapter = adapter
            adapter?.notifyDataSetChanged()
        }
    }
}