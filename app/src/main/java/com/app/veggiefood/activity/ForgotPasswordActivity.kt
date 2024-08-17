package com.app.veggiefood.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.app.veggiefood.R
import com.app.veggiefood.databinding.ActivityForgotPasswordBinding
import com.app.veggiefood.extensions.isNetworkAvailable
import com.app.veggiefood.extensions.isValidateEmail
import com.app.veggiefood.model.request.ForgotPasswordRequest
import com.app.veggiefood.viewmodel.ForgotPasswordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordActivity : AppCompatActivity() {

    lateinit var binding: ActivityForgotPasswordBinding
    lateinit var mContext: ForgotPasswordActivity
    private val viewModel: ForgotPasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password)
        mContext = this
        initUI()
        addListner()
        addObsereves()
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
                    viewModel.doForgotPassword(ForgotPasswordRequest(
                        user_email = binding.edtEmailAddress.text.toString().trim()
                    ))
                }

            } else {
                Toast.makeText(
                    mContext, getString(R.string.str_error_internet_connections),
                    Toast.LENGTH_SHORT
                ).show()
            }
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

        viewModel.forgotPasswordLiveData.observe(this, Observer {
            if (it.status == "success") {
                Toast.makeText(mContext, it.message, Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(mContext, it.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}