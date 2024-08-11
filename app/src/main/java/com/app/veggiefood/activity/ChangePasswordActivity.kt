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
import com.app.veggiefood.databinding.ActivityChangePasswordBinding
import com.app.veggiefood.extensions.isNetworkAvailable
import com.app.veggiefood.extensions.isValidConfirmNewPassword
import com.app.veggiefood.extensions.isValidNewPassword
import com.app.veggiefood.extensions.isValidOldPassword
import com.app.veggiefood.extensions.isValidPasswordAndConfirmPassword
import com.app.veggiefood.model.request.ChangePasswordRequest
import com.app.veggiefood.util.PrefManager
import com.app.veggiefood.util.StaticData
import com.app.veggiefood.viewmodel.ChangePasswordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordActivity : AppCompatActivity() {

    lateinit var binding: ActivityChangePasswordBinding
    lateinit var mContext: ChangePasswordActivity
    private val viewModel: ChangePasswordViewModel by viewModels()
    private var isPasswordVisible = false
    private var isNewPasswordVisible = false
    private var isConfirmNewPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_password)
        mContext = this
        initUI()
        addListner()
        addObsereves()
    }

    private fun initUI() {

    }

    private fun addListner() {
        binding.apply {
            ivBack.setOnClickListener {
                finish()
            }

            binding.ivImage.setOnClickListener {
                togglePasswordVisibility()
            }
            binding.ivNewPasswordImage.setOnClickListener {
                newTogglePasswordVisibility()
            }
            binding.ivConfirmNewPasswordImage.setOnClickListener {
                confirmNewTogglePasswordVisibility()
            }

            tvSubmit.setOnClickListener {
                if (isNetworkAvailable(mContext)) {
                    if (isValidOldPassword(mContext, binding.edtOldPassword.text.toString().trim())
                        && isValidNewPassword(
                            mContext,
                            binding.edtNewPassword.text.toString().trim()
                        )
                        && isValidConfirmNewPassword(
                            mContext,
                            binding.edtConfirmNewPassword.text.toString().trim()
                        )
                        && isValidPasswordAndConfirmPassword(
                            mContext, binding.edtNewPassword.text.toString().trim(),
                            binding.edtConfirmNewPassword.text.toString().trim()
                        )
                    ) {
                        viewModel.doChangePassword(
                            ChangePasswordRequest(
                                PrefManager(mContext).getvalue(StaticData.id).toString(),
                                binding.edtOldPassword.text.toString().trim(),
                                binding.edtNewPassword.text.toString().trim()
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
    }

    private fun togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Hide Password
            binding.edtOldPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.ivImage.setImageResource(R.drawable.ic_password_hide)
        } else {
            // Show Password
            binding.edtOldPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.ivImage.setImageResource(R.drawable.ic_password_show)
        }
        // Move the cursor to the end of the text
        binding.edtOldPassword.setSelection(binding.edtOldPassword.length())
        isPasswordVisible = !isPasswordVisible
    }


    private fun newTogglePasswordVisibility() {
        if (isNewPasswordVisible) {
            // Hide Password
            binding.edtNewPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.ivNewPasswordImage.setImageResource(R.drawable.ic_password_hide)
        } else {
            // Show Password
            binding.edtNewPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.ivNewPasswordImage.setImageResource(R.drawable.ic_password_show)
        }
        // Move the cursor to the end of the text
        binding.edtNewPassword.setSelection(binding.edtNewPassword.length())
        isNewPasswordVisible = !isNewPasswordVisible
    }


    private fun confirmNewTogglePasswordVisibility() {
        if (isConfirmNewPasswordVisible) {
            // Hide Password
            binding.edtConfirmNewPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.ivConfirmNewPasswordImage.setImageResource(R.drawable.ic_password_hide)
        } else {
            // Show Password
            binding.edtConfirmNewPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.ivConfirmNewPasswordImage.setImageResource(R.drawable.ic_password_show)
        }
        // Move the cursor to the end of the text
        binding.edtConfirmNewPassword.setSelection(binding.edtConfirmNewPassword.length())
        isConfirmNewPasswordVisible = !isConfirmNewPasswordVisible
    }


    private fun addObsereves() {
        viewModel.isLoading.observe(this, Observer {
            if (it) {
                binding.pbLoadData.visibility = View.VISIBLE
            } else {
                binding.pbLoadData.visibility = View.GONE
            }
        })

        viewModel.changePasswordLiveData.observe(this, Observer {
            if (it.status) {
                Toast.makeText(mContext, it.message, Toast.LENGTH_SHORT).show()
                PrefManager(mContext).clearValue()
                startActivity(Intent(mContext, LoginActivity::class.java))
                finishAffinity()
            } else {
                Toast.makeText(mContext, it.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}