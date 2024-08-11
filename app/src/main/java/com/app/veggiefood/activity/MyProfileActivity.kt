package com.app.veggiefood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.app.veggiefood.R
import com.app.veggiefood.databinding.ActivityMyProfileBinding
import com.app.veggiefood.extensions.isNetworkAvailable
import com.app.veggiefood.extensions.isValidAddress
import com.app.veggiefood.extensions.isValidCity
import com.app.veggiefood.extensions.isValidFirstName
import com.app.veggiefood.extensions.isValidLastName
import com.app.veggiefood.extensions.isValidPhoneNumber
import com.app.veggiefood.extensions.isValidPinCode
import com.app.veggiefood.extensions.isValidState
import com.app.veggiefood.extensions.isValidateEmail
import com.app.veggiefood.model.request.DeleteUserRequest
import com.app.veggiefood.model.request.ProfileRequest
import com.app.veggiefood.model.response.ProfileModel
import com.app.veggiefood.util.PrefManager
import com.app.veggiefood.util.StaticData
import com.app.veggiefood.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyProfileActivity : AppCompatActivity() {

    lateinit var binding: ActivityMyProfileBinding
    lateinit var mContext: MyProfileActivity
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_profile)
        mContext = this
        initUI()
        addListner()
        addObsereves()
    }

    private fun initUI() {
        if (isNetworkAvailable(mContext)) {
            viewModel.getProfile(PrefManager(mContext).getvalue(StaticData.id).toString())
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

        binding.tvDeleteAccount.setOnClickListener {
            deleteAccountDialog()
        }

        binding.tvUpdateProfile.setOnClickListener {
            if (isNetworkAvailable(mContext)) {
                if (isValidFirstName(mContext, binding.edtFirstName.text.toString().trim())
                    && isValidLastName(mContext, binding.edtLastName.text.toString().trim())
                    && isValidateEmail(mContext, binding.edtEmailAddress.text.toString().trim())
                    && isValidPhoneNumber(mContext, binding.edtPhoneNumber.text.toString().trim())
                    && isValidAddress(mContext, binding.edtAddress.text.toString().trim())
                    && isValidState(mContext, binding.edtStae.text.toString().trim())
                    && isValidCity(mContext, binding.edtCity.text.toString().trim())
                    && isValidPinCode(mContext, binding.edtPinCode.text.toString().trim())
                ) {
                    viewModel.updateProfile(
                        PrefManager(mContext).getvalue(StaticData.id).toString(),
                        ProfileRequest(
                            fname = binding.edtFirstName.text.toString().trim(),
                            lname = binding.edtLastName.text.toString().trim(),
                            email = binding.edtEmailAddress.text.toString().trim(),
                            phone = binding.edtPhoneNumber.text.toString().trim(),
                            address = binding.edtAddress.text.toString().trim(),
                            city = binding.edtCity.text.toString().trim(),
                            state = binding.edtStae.text.toString().trim(),
                            pincode = binding.edtPinCode.text.toString().trim()
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

    private fun deleteAccountDialog() {
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Delete Account")
        builder.setMessage("Are you sure you want to Delete Account?")

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            dialog.dismiss()
            viewModel.deleteAccount(
                DeleteUserRequest(
                    PrefManager(mContext).getvalue(StaticData.id).toString()
                )
            )

        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            dialog.dismiss()
        }

        builder.show()
    }


    private fun addObsereves() {
        viewModel.isLoading.observe(this, Observer {
            if (it) {
                binding.pbLoadData.visibility = View.VISIBLE
            } else {
                binding.pbLoadData.visibility = View.GONE
            }

        })

        viewModel.profileLiveData.observe(this, Observer {
            if (it.status) {
                setData(it.data)
            } else {
            }
        })

        viewModel.updateProfileLiveData.observe(this, Observer {
            if (it.status) {
                Toast.makeText(mContext, it.message, Toast.LENGTH_SHORT).show()
                setPrefData()
            } else {
                Toast.makeText(mContext, it.message, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.deleteUserLiveData.observe(this, Observer {
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

    private fun setPrefData() {
        PrefManager(mContext).setvalue(
            StaticData.fname, binding.edtFirstName.text.toString().trim()
        )
        PrefManager(mContext).setvalue(
            StaticData.lname,
            binding.edtLastName.text.toString().trim()
        )
        PrefManager(mContext).setvalue(
            StaticData.email,
            binding.edtEmailAddress.text.toString().trim()
        )
        PrefManager(mContext).setvalue(
            StaticData.phone,
            binding.edtPhoneNumber.text.toString().trim()
        )
        finish()

    }

    private fun setData(data: ProfileModel) {
        binding.apply {
            edtFirstName.setText(data.fname)
            edtLastName.setText(data.lname)
            edtEmailAddress.setText(data.email)
            edtPhoneNumber.setText(data.phone)
            edtAddress.setText(data.address)
            edtStae.setText(data.state)
            edtCity.setText(data.city)
            edtPinCode.setText(data.pincode)
        }
    }
}