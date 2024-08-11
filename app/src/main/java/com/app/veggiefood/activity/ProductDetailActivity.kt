package com.app.veggiefood.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.text.Html
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.app.veggiefood.R
import com.app.veggiefood.adapter.CustomArrayAdapter
import com.app.veggiefood.adapter.ProductImagesAdapter
import com.app.veggiefood.databinding.ActivityProductDetailBinding
import com.app.veggiefood.extensions.isNetworkAvailable
import com.app.veggiefood.model.request.AddEditCartRequest
import com.app.veggiefood.model.request.CalculatePriceRequest
import com.app.veggiefood.model.response.ProductDetailModel
import com.app.veggiefood.model.response.VariationModel
import com.app.veggiefood.util.PrefManager
import com.app.veggiefood.util.StaticData
import com.app.veggiefood.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.math.BigDecimal
import java.math.RoundingMode


@AndroidEntryPoint
class ProductDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityProductDetailBinding
    lateinit var mContext: ProductDetailActivity
    private var id: String? = ""
    private var cartId: String? = ""
    private val viewModel: ProductViewModel by viewModels()
    private val variationList: ArrayList<VariationModel> = arrayListOf()
    private val stringVariationList = arrayListOf<String>()
    private var variationId: String? = ""
    private var price: String? = ""
    private var mFrom: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)
        mContext = this
        initUI()
        addListner()
        addObsereves()
    }

    private fun initUI() {
        if (intent.extras != null) {
            id = intent.getStringExtra("id")
            mFrom = intent.getStringExtra("mFrom")

            if (mFrom.equals("isAdd")) {

                binding.addToCartText.text = "ADD TO CART"
            } else {
                cartId = intent.getStringExtra("cartId")
                binding.addToCartText.text = "Edit TO CART"
            }
        }

        if (isNetworkAvailable(mContext)) {
            viewModel.getProductDetail(id.toString())
            viewModel.getVariation()
        } else {
            Toast.makeText(
                mContext, getString(R.string.str_error_internet_connections), Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun addListner() {
        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.acVariation.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                binding.acVariation.showDropDown()
                return false
            }

        })

        binding.tvGetPrice.setOnClickListener {
            if (isNetworkAvailable(mContext)) {
                if (isValidate()) {
                    viewModel.getCalculatePrice(
                        CalculatePriceRequest(
                            variationId.toString(),
                            id.toString(),
                            binding.qtyValue.text.toString().trim()
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

        binding.addToCartBtn.setOnClickListener {
            if (isNetworkAvailable(mContext)) {
                if (isAddEditValidate()) {
                    if (mFrom.equals("isAdd")) {
                        viewModel.addEditCartApi(
                            AddEditCartRequest(
                                "", variationId.toString(),
                                PrefManager(mContext).getvalue(StaticData.id).toString(),
                                id.toString(),
                                price.toString(),
                                binding.qtyValue.text.toString().trim()
                            )
                        )

                    } else {
                        viewModel.addEditCartApi(
                            AddEditCartRequest(
                                cartId.toString(), variationId.toString(),
                                PrefManager(mContext).getvalue(StaticData.id).toString(),
                                id.toString(),
                                price.toString(),
                                binding.qtyValue.text.toString().trim()
                            )
                        )
                    }
                }
            } else {
                Toast.makeText(
                    mContext, getString(R.string.str_error_internet_connections),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.qtyInc.setOnClickListener {
            var count: Int = binding.qtyValue.text.toString().toInt()
            count++
            binding.qtyValue.text = count.toString()
        }

        binding.qtyDec.setOnClickListener {
            var count: Int = binding.qtyValue.text.toString().toInt()
            if (count > 1) {
                count--
                binding.qtyValue.text = count.toString()
            } else {
                Toast.makeText(
                    this@ProductDetailActivity,
                    "Qty cannot be less than 1.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.buyNowBtn.setOnClickListener {
            startActivity(Intent(mContext, MyCartActivity::class.java))
        }
    }

    private fun isValidate(): Boolean {
        var isValid = true
        if (variationId!!.isEmpty()) {
            Toast.makeText(mContext, "Please select variation", Toast.LENGTH_SHORT).show()
            isValid = false
        }
        return isValid
    }

    private fun isAddEditValidate(): Boolean {
        var isValid = true
        if (variationId!!.isEmpty()) {
            Toast.makeText(mContext, "Please select variation", Toast.LENGTH_SHORT).show()
            isValid = false
        } else if (price!!.isEmpty()) {
            Toast.makeText(mContext, "Please calculate the price", Toast.LENGTH_SHORT).show()
            isValid = false
        }
        return isValid

    }

    private fun addObsereves() {
        viewModel.isLoading.observe(this, Observer {
            if (it) {
                binding.pbLoadData.visibility = View.VISIBLE
            } else {
                binding.pbLoadData.visibility = View.GONE
            }
        })

        viewModel.productDetailLiveData.observe(this, Observer {
            if (it.status) {
                setProductData(it.data)
            } else {

            }
        })

        viewModel.variationLiveData.observe(this, Observer {
            if (it.status) {
                variationList.clear()
                variationList.addAll(it.data)
                setVariationData(variationList)

            }
        })

        viewModel.addEditCartLiveData.observe(this, Observer {
            if (it.status == "success") {
                if (mFrom.equals("isAdd")) {
                    Toast.makeText(mContext, it.message, Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    val intent = Intent()
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
            } else {
                Toast.makeText(mContext, it.message, Toast.LENGTH_SHORT).show()
            }

        })


        viewModel.calculatePriceLiveData.observe(this, Observer {
            if (it.status == "success") {
                try {
                    val rounded: Double =
                        BigDecimal(it.price.toDouble()).setScale(2, RoundingMode.HALF_UP)
                            .toDouble()
                    binding.pPrice.text = "Discount Price:- ₹$rounded"
                    price = rounded.toString()
                } catch (e: Exception) {
                    binding.pPrice.text = "Discount Price:- ₹${it.price}"
                    price = it.price.toString()
                }
            } else {

            }
        })


    }

    private fun setVariationData(variationList: ArrayList<VariationModel>) {
        stringVariationList.clear()
        for (i in 0 until variationList.size) {
            stringVariationList.add(variationList[i].variation)
        }

        val arrayAdapter: CustomArrayAdapter =
            CustomArrayAdapter(mContext, stringVariationList)
        binding.acVariation.threshold = 0
        binding.acVariation.dropDownVerticalOffset = 0
        binding.acVariation.setAdapter(arrayAdapter)

        binding.acVariation.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                variationId = variationList[position].id
            }

    }

    private fun setProductData(data: ProductDetailModel) {
        val viewPagerAdapter = ProductImagesAdapter(data.image)
        binding.viewPager.adapter = viewPagerAdapter

        //set the orientation of the viewpager using ViewPager2.orientation
        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        //select any page you want as your starting page
        val currentPageIndex = 1
        binding.viewPager.currentItem = currentPageIndex

        // registering for page change callback
        binding.viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {

            }
        )

        binding.tvProductName.text = data.name
        binding.pPrice.text = "Discount Price:- ₹" + data.discounted_price
        binding.pSPrice.text = "Price:- ₹" + data.base_price
        binding.pSPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        binding.tvDesc.text = Html.fromHtml(data.description)


    }
}