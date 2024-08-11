package com.app.veggiefood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.app.veggiefood.R
import com.app.veggiefood.databinding.ActivitySignupBinding
import com.app.veggiefood.extensions.isNetworkAvailable
import com.app.veggiefood.extensions.isValidConfirmPassword
import com.app.veggiefood.extensions.isValidFirstName
import com.app.veggiefood.extensions.isValidLastName
import com.app.veggiefood.extensions.isValidPassword
import com.app.veggiefood.extensions.isValidPasswordAndConfirmPassword
import com.app.veggiefood.extensions.isValidPhoneNumber
import com.app.veggiefood.extensions.isValidateEmail
import com.app.veggiefood.model.request.SignupRequest
import com.app.veggiefood.viewmodel.SignupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignupBinding
    lateinit var mContext: SignupActivity
    private val viewModel: SignupViewModel by viewModels()
    private var isPasswordVisible = false
    private var isConfirmPasswordVisible = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        mContext = this
        initUI()
        addListner()
        addObserves()
    }

    private fun initUI() {


    }

    private fun addListner() {
        binding.tvLogin.setOnClickListener {
            startActivity(Intent(mContext, LoginActivity::class.java))
            finishAffinity()
        }

        binding.ivImage.setOnClickListener {
            togglePasswordVisibility()
        }
        binding.ivConfirmPasswordImage.setOnClickListener {
            confirmTogglePasswordVisibility()
        }

        binding.tvSignup.setOnClickListener {
            if (isNetworkAvailable(mContext)) {
                if (isValidFirstName(
                        mContext,
                        binding.edtFirstName.text.toString().trim()
                    ) && isValidLastName(
                        mContext,
                        binding.edtLastName.text.toString().trim()
                    ) && isValidateEmail(
                        mContext,
                        binding.edtEmailAddress.text.toString().trim()
                    ) && isValidPhoneNumber(
                        mContext,
                        binding.edtPhoneNumber.text.toString().trim()
                    ) && isValidPassword(
                        mContext,
                        binding.edtPassword.text.toString().trim()
                    ) && isValidConfirmPassword(
                        mContext, binding.edtConfirmPassword.text.toString().trim()
                    ) && isValidPasswordAndConfirmPassword(
                        mContext,
                        binding.edtPassword.text.toString().trim(),
                        binding.edtConfirmPassword.text.toString().trim()
                    )
                ) {
                    viewModel.doSignup(
                        SignupRequest(
                            binding.edtFirstName.text.toString().trim(),
                            binding.edtLastName.text.toString().trim(),
                            binding.edtEmailAddress.text.toString().trim(),
                            binding.edtPhoneNumber.text.toString().trim(),
                            binding.edtPassword.text.toString().trim(),
                            binding.edtConfirmPassword.text.toString().trim(),
                            "accept"
                        )
                    )
                }
            } else {
                Toast.makeText(
                    mContext, getString(R.string.str_error_internet_connections), Toast.LENGTH_SHORT
                ).show()
            }
        }
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


    private fun confirmTogglePasswordVisibility() {
        if (isConfirmPasswordVisible) {
            // Hide Password
            binding.edtConfirmPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.ivConfirmPasswordImage.setImageResource(R.drawable.ic_password_hide)
        } else {
            // Show Password
            binding.edtConfirmPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.ivConfirmPasswordImage.setImageResource(R.drawable.ic_password_show)
        }
        // Move the cursor to the end of the text
        binding.edtConfirmPassword.setSelection(binding.edtConfirmPassword.length())
        isConfirmPasswordVisible = !isConfirmPasswordVisible
    }


    private fun addObserves() {
        viewModel.isLoading.observe(this, Observer {
            if (it) {
                binding.pbLoadData.visibility = View.VISIBLE
            } else {
                binding.pbLoadData.visibility = View.GONE
            }

        })

        viewModel.signupLiveData.observe(this, Observer {
            if (it.status) {
                Toast.makeText(mContext, it.message, Toast.LENGTH_SHORT).show()
                startActivity(Intent(mContext, LoginActivity::class.java))
                finishAffinity()
            } else {
                Toast.makeText(mContext, it.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

}