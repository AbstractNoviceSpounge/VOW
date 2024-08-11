package com.app.veggiefood.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.veggiefood.R
import com.app.veggiefood.adapter.OrderDetailAdapter
import com.app.veggiefood.databinding.ActivityOrderDetailBinding
import com.app.veggiefood.model.response.OrderModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityOrderDetailBinding
    lateinit var mContext: OrderDetailActivity
    lateinit var orderModel: OrderModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_detail)
        mContext = this
        initUI()
        addListner()
    }

    private fun initUI() {
        if (intent.extras != null) {
            orderModel = Gson().fromJson(intent.getStringExtra("model"), OrderModel::class.java)
            setData(orderModel)
        }
    }

    private fun addListner() {
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setData(orderModel: OrderModel) {
        binding.apply {
            tvName.text = "Name:- " + orderModel.fname + " " + orderModel.lname
            tvPhoneNumber.text = "Phone Number:- " + orderModel.phone
            tvAddress.text = "Address:- " + orderModel.address + ", " + orderModel.city + ", " + orderModel.state + ", " + orderModel.pincode
            tvOrderId.text = "Order Id:- " + orderModel.order_id
            tvDate.text = "Date:- " + orderModel.date
            tvShippingMethod.text = "Shipping Method:- " + orderModel.shipping_method
            tvPrice.text = "Total Price:- " + orderModel.final_price
            rvOrders.layoutManager = LinearLayoutManager(mContext)
            rvOrders.setHasFixedSize(true)
            rvOrders.addItemDecoration(
                DividerItemDecoration(
                    mContext,
                    LinearLayoutManager.VERTICAL
                )
            )
            adapter = OrderDetailAdapter(mContext, orderModel.products)
            rvOrders.adapter = adapter
        }
    }
}