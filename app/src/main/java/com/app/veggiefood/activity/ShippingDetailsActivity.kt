package com.app.veggiefood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import android.widget.RadioGroup.OnCheckedChangeListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.app.veggiefood.R
import com.app.veggiefood.databinding.ActivityShippingDetailsBinding
import com.app.veggiefood.extensions.isNetworkAvailable
import com.app.veggiefood.extensions.isSelectPaymentMethod
import com.app.veggiefood.extensions.isValidAddress
import com.app.veggiefood.extensions.isValidCity
import com.app.veggiefood.extensions.isValidFirstName
import com.app.veggiefood.extensions.isValidLastName
import com.app.veggiefood.extensions.isValidPhoneNumber
import com.app.veggiefood.extensions.isValidPinCode
import com.app.veggiefood.extensions.isValidState
import com.app.veggiefood.extensions.isValidateEmail
import com.app.veggiefood.model.request.PlaceOrderRequest
import com.app.veggiefood.model.request.ProfileRequest
import com.app.veggiefood.util.PrefManager
import com.app.veggiefood.util.StaticData
import com.app.veggiefood.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShippingDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityShippingDetailsBinding
    lateinit var mContext: ShippingDetailsActivity
    private var selectPaymentMethod: String? = ""
    private val viewModel: ProductViewModel by viewModels()
    private var cartId: String? = ""
    private var totalPrice: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shipping_details)
        mContext = this
        initUI()
        addListner()
        addObsereves()
    }

    private fun initUI() {
        if (intent.extras != null) {
            cartId = intent.getStringExtra("cartId")
            totalPrice = intent.getStringExtra("price")
        }
    }

    private fun addListner() {
        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.tvPlaceOrder.setOnClickListener {
            if (isNetworkAvailable(mContext)) {
                if (isValidFirstName(mContext, binding.edtFirstName.text.toString().trim())
                    && isValidLastName(mContext, binding.edtLastName.text.toString().trim())
                    && isValidateEmail(mContext, binding.edtEmailAddress.text.toString().trim())
                    && isValidPhoneNumber(mContext, binding.edtPhoneNumber.text.toString().trim())
                    && isValidAddress(mContext, binding.edtAddress.text.toString().trim())
                    && isValidState(mContext, binding.edtStae.text.toString().trim())
                    && isValidCity(mContext, binding.edtCity.text.toString().trim())
                    && isValidPinCode(mContext, binding.edtPinCode.text.toString().trim())
                    && isSelectPaymentMethod(mContext, selectPaymentMethod.toString())
                ) {
                    viewModel.placeOrder(
                        PlaceOrderRequest(
                            PrefManager(mContext).getvalue(StaticData.id).toString(),
                            binding.edtFirstName.text.toString().trim(),
                            binding.edtLastName.text.toString().trim(),
                            binding.edtPhoneNumber.text.toString().trim(),
                            binding.edtEmailAddress.text.toString().trim(),
                            binding.edtAddress.text.toString().trim(),
                            binding.edtCity.text.toString().trim(),
                            binding.edtStae.text.toString().trim(),
                            binding.edtPinCode.text.toString().trim(),
                            totalPrice.toString(),
                            totalPrice.toString(),
                            selectPaymentMethod.toString(),
                            System.currentTimeMillis().toString(),
                            "",
                            cartId.toString()

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

        binding.rbCod.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                selectPaymentMethod = "cod"
                binding.rbCod.isChecked = true
                binding.rbOnline.isChecked = false
            }
        }

        binding.rbOnline.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                selectPaymentMethod = "Online Payment"
                binding.rbCod.isChecked = false
                binding.rbOnline.isChecked = true
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

        viewModel.placeOrderLiveData.observe(this, Observer {
            if (it.status == "success") {
                startActivity(Intent(mContext, ActivityOrderStatusActivity::class.java))
                finishAffinity()
            } else {
                Toast.makeText(mContext, it.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}