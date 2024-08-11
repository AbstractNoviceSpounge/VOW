package com.app.veggiefood.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.app.veggiefood.R
import com.app.veggiefood.databinding.ActivityTrackOrderBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackOrderActivity : AppCompatActivity() {

    lateinit var binding: ActivityTrackOrderBinding
    lateinit var mContext: TrackOrderActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_track_order)
        mContext = this
        initUI()
        addListner()
    }
    private fun initUI(){

    }
    private fun addListner(){
        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}