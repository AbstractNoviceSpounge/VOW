package com.app.veggiefood.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.veggiefood.R
import com.app.veggiefood.databinding.ActivityOrderStatusBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityOrderStatusActivity : AppCompatActivity() {

    lateinit var binding: ActivityOrderStatusBinding
    lateinit var mContext: ActivityOrderStatusActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_status)
        mContext = this
        initUI()
        addListner()
    }

    private fun initUI(){

    }
    private fun addListner(){
        binding.homeBtn.setOnClickListener({
            finish()
            startActivity(Intent(mContext, MainActivity::class.java))
        })

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        startActivity(Intent(mContext, MainActivity::class.java))

    }
}