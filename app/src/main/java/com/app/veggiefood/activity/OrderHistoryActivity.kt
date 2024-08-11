package com.app.veggiefood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.veggiefood.R
import com.app.veggiefood.adapter.OrderHistoryAdapter
import com.app.veggiefood.databinding.ActivityOrderHistoryBinding
import com.app.veggiefood.extensions.isNetworkAvailable
import com.app.veggiefood.model.response.OrderModel
import com.app.veggiefood.util.PrefManager
import com.app.veggiefood.util.StaticData
import com.app.veggiefood.viewmodel.OrderViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderHistoryActivity : AppCompatActivity(),OrderHistoryAdapter.onItemClick {

    lateinit var binding: ActivityOrderHistoryBinding
    lateinit var mContext: OrderHistoryActivity
    private val viewModel: OrderViewModel by viewModels()
    private var orderList: ArrayList<OrderModel> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_history)
        mContext = this
        initUI()
        addListner()
        addObsereves()
    }

    private fun initUI() {

        if (isNetworkAvailable(mContext)) {
            viewModel.getOrders(PrefManager(mContext).getvalue(StaticData.id).toString())
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

    private fun addObsereves() {
        viewModel.isLoading.observe(this, Observer {
            if (it) {
                binding.pbLoadData.visibility = View.VISIBLE
            } else {
                binding.pbLoadData.visibility = View.GONE
            }
        })

        viewModel.isOrderLiveData.observe(this, Observer {
            if (it.status) {
                orderList.clear()
                orderList.addAll(it.data)
                setData(orderList)
            } else {
                binding.rvOrders.visibility = View.GONE
                binding.tvNoData.visibility = View.VISIBLE
            }
        })
    }
    private fun setData(orderList: ArrayList<OrderModel>) {
        binding.apply {
            rvOrders.layoutManager=LinearLayoutManager(mContext)
            rvOrders.setHasFixedSize(true)
            adapter= OrderHistoryAdapter(mContext,orderList,this@OrderHistoryActivity)
            rvOrders.adapter=adapter
            adapter?.notifyDataSetChanged()
        }
    }

    override fun onClick(position: Int, orderModel: OrderModel) {
        startActivity(Intent(mContext,OrderDetailActivity::class.java)
            .putExtra("model",Gson().toJson(orderModel)))
    }
}