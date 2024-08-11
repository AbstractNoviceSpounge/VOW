package com.app.veggiefood.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.app.veggiefood.R
import com.app.veggiefood.databinding.ActivityLoginBinding
import com.app.veggiefood.extensions.isNetworkAvailable
import com.app.veggiefood.extensions.isValidPassword
import com.app.veggiefood.extensions.isValidateEmail
import com.app.veggiefood.model.request.LoginRequest
import com.app.veggiefood.model.response.LoginModel
import com.app.veggiefood.util.PrefManager
import com.app.veggiefood.util.StaticData
import com.app.veggiefood.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var mContext: LoginActivity
    private val viewModel: LoginViewModel by viewModels()
    private var isPasswordVisible = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        mContext = this
        initUI()
        addListner()
        addObserves()
    }

    private fun initUI() {

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun addListner() {
        binding.tvForogtPassword.setOnClickListener {
            startActivity(Intent(mContext, ForgotPasswordActivity::class.java))
        }
        binding.tvSignup.setOnClickListener {
            startActivity(Intent(mContext, SignupActivity::class.java))
        }

        binding.ivImage.setOnClickListener {
            togglePasswordVisibility()
        }



        binding.tvLogin.setOnClickListener {
            if (isNetworkAvailable(mContext)) {
                if (isValidateEmail(
                        mContext,
                        binding.edtEmailAddress.text.toString().trim()
                    ) && isValidPassword(
                        mContext, binding.edtPassword.text.toString().trim()
                    )
                ) {
                    viewModel.doLogin(
                        LoginRequest(
                            email = binding.edtEmailAddress.text.toString().trim(),
                            password = binding.edtPassword.text.toString().trim()
                        )
                    )
                }
            } else {
                Toast.makeText(
                    mContext, getString(R.string.str_error_internet_connections),
                    Toast.LENGTH_SHORT
                ).show()
            }
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

        viewModel.loginLiveData.observe(this, Observer {
            if (it.status) {
                Toast.makeText(mContext, it.message, Toast.LENGTH_SHORT).show()
                setData(it.user)
            } else {
                Toast.makeText(mContext, it.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setData(data: LoginModel) {
        PrefManager(mContext).setvalue(StaticData.isLogin, true)
        PrefManager(mContext).setvalue(StaticData.id, data.id)
        PrefManager(mContext).setvalue(StaticData.fname, data.fname)
        PrefManager(mContext).setvalue(StaticData.lname, data.lname)
        PrefManager(mContext).setvalue(StaticData.email, data.email)
        PrefManager(mContext).setvalue(StaticData.phone, data.phone)

        startActivity(Intent(mContext, MainActivity::class.java))
        finishAffinity()
    }

    private fun togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Hide Password
            binding.edtPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.ivImage.setImageResource(R.drawable.ic_password_hide)
        } else {
            // Show Password
            binding.edtPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.ivImage.setImageResource(R.drawable.ic_password_show)
        }
        // Move the cursor to the end of the text
        binding.edtPassword.setSelection(binding.edtPassword.length())
        isPasswordVisible = !isPasswordVisible
    }

}