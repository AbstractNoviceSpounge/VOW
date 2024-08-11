package com.app.veggiefood.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.app.veggiefood.R
import com.app.veggiefood.databinding.ActivityForgotPasswordBinding
import com.app.veggiefood.extensions.isNetworkAvailable
import com.app.veggiefood.extensions.isValidateEmail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordActivity : AppCompatActivity() {

    lateinit var binding: ActivityForgotPasswordBinding
    lateinit var mContext: ForgotPasswordActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password)
        mContext = this
        initUI()
        addListner()
    }

    private fun initUI() {

    }

    private fun addListner() {
        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.tvSubmit.setOnClickListener {
            if (isNetworkAvailable(mContext)) {
                if (isValidateEmail(mContext, binding.edtEmailAddress.text.toString().trim())) {
                    finish()
                }

            } else {
                Toast.makeText(
                    mContext, getString(R.string.str_error_internet_connections),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}