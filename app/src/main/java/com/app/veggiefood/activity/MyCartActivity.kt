package com.app.veggiefood.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.veggiefood.R
import com.app.veggiefood.adapter.MyCartAdapter
import com.app.veggiefood.databinding.ActivityMyCartBinding
import com.app.veggiefood.extensions.isNetworkAvailable
import com.app.veggiefood.model.request.CartRequest
import com.app.veggiefood.model.request.DeleteCartRequest
import com.app.veggiefood.model.response.CartModel
import com.app.veggiefood.util.PrefManager
import com.app.veggiefood.util.StaticData
import com.app.veggiefood.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyCartActivity : AppCompatActivity(), MyCartAdapter.onItemClick {

    lateinit var binding: ActivityMyCartBinding
    lateinit var mContext: MyCartActivity
    private val viewModel: ProductViewModel by viewModels()
    private var cartList: ArrayList<CartModel> = arrayListOf()
    private var cartPosition: Int = 0
    private var totalPrice: String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_cart)
        mContext = this
        initUI()
        addListner()
        addObsereves()
    }

    private fun initUI() {
        if (isNetworkAvailable(mContext)) {
            viewModel.getCart(CartRequest(PrefManager(mContext).getvalue(StaticData.id).toString()))
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
        binding.tvCheckout.setOnClickListener {
            var cartId = ""
            for (i in 0..<cartList.size) {
                cartId = if (cartId.isEmpty()) {
                    cartList[i].cart_id.toString()
                } else {
                    cartId + "," + cartList[i].cart_id.toString()
                }
            }

            Log.e("cartId", "=$cartId")

            startActivity(
                Intent(mContext, ShippingDetailsActivity::class.java)
                    .putExtra("cartId", cartId)
                    .putExtra("price", totalPrice)
            )
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

        viewModel.myCartLiveData.observe(this, Observer {
            if (it.status == "success") {
                cartList.clear()
                cartList.addAll(it.data)
                if (cartList.size > 0) {
                    binding.rvCart.visibility = View.VISIBLE
                    binding.tvNoDataFound.visibility = View.GONE
                    binding.cartTotal.visibility = View.VISIBLE
                    binding.tvPaybleAmount.text = "₹ " + it.total_cost.toString()
                    totalPrice = it.total_cost.toString()
                    setData(cartList)
                } else {
                    binding.cartTotal.visibility = View.GONE
                    binding.rvCart.visibility = View.GONE
                    binding.tvNoDataFound.visibility = View.VISIBLE
                }
            } else {

            }
        })

        viewModel.deleteCartLiveData.observe(this, Observer {
            if (it.status == "success") {
                Toast.makeText(mContext, it.message, Toast.LENGTH_SHORT).show()
                if (cartList.size > 0) {
                    cartList.removeAt(cartPosition)
                    viewModel.getCart(
                        CartRequest(
                            PrefManager(mContext).getvalue(StaticData.id).toString()
                        )
                    )
                } else {
                    binding.rvCart.setVisibility(View.GONE)
                    binding.tvNoDataFound.setVisibility(View.VISIBLE)
                }
            } else {
                Toast.makeText(mContext, it.message, Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun setData(cartList: ArrayList<CartModel>) {
        binding.apply {
            rvCart.layoutManager = LinearLayoutManager(mContext)
            rvCart.setHasFixedSize(true)
            adapter = MyCartAdapter(mContext, cartList, this@MyCartActivity)
            rvCart.adapter = adapter
            adapter?.notifyDataSetChanged()
        }
    }

    override fun onEditClick(position: Int, cartModel: CartModel) {
        startActivityForResult(
            Intent(mContext, ProductDetailActivity::class.java)
                .putExtra("id", cartModel.product.id.toString())
                .putExtra("mFrom", "isEdit")
                .putExtra("cartId", cartModel.cart_id.toString()), 201
        )
    }

    override fun onDeleteClick(position: Int, cartModel: CartModel) {
        deleteDialog(position, cartModel)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 201) {
            if (resultCode == Activity.RESULT_OK) {
                if (isNetworkAvailable(mContext)) {
                    viewModel.getCart(
                        CartRequest(
                            PrefManager(mContext).getvalue(StaticData.id).toString()
                        )
                    )
                } else {
                    Toast.makeText(
                        mContext, getString(R.string.str_error_internet_connections),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun deleteDialog(position: Int, cartModel: CartModel) {
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Delete")
        builder.setMessage("Are you sure you want to Delete Product in cart?")

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            if (isNetworkAvailable(mContext)) {
                cartPosition = position
                viewModel.deleteCart(
                    DeleteCartRequest(
                        PrefManager(mContext).getvalue(StaticData.id).toString(),
                        cartModel.cart_id.toString()
                    )
                )
            } else {
                Toast.makeText(
                    mContext, getString(R.string.str_error_internet_connections),
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            dialog.dismiss()
        }

        builder.show()
    }

}