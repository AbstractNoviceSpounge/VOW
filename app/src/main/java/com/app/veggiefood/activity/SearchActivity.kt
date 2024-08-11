package com.app.veggiefood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.app.veggiefood.R
import com.app.veggiefood.adapter.ProductAdapter
import com.app.veggiefood.adapter.SearchAdapter
import com.app.veggiefood.databinding.ActivitySearchBinding
import com.app.veggiefood.extensions.isNetworkAvailable
import com.app.veggiefood.model.response.ProductModel
import com.app.veggiefood.model.response.SearchModel
import com.app.veggiefood.util.Utils
import com.app.veggiefood.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity(), SearchAdapter.onItemClick {

    lateinit var binding: ActivitySearchBinding
    lateinit var mContext: SearchActivity
    private val viewModel: ProductViewModel by viewModels()
    private var searchList: ArrayList<SearchModel> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
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
        binding.tvSearch.setOnClickListener {
            if (isNetworkAvailable(mContext)) {
                if (isValidate()) {
                    Utils.hideSoftKeyBoard(mContext, binding.edtSearch)
                    viewModel.searchProduct(binding.edtSearch.text.toString().trim())
                }
            } else {
                Toast.makeText(
                    mContext, getString(R.string.str_error_internet_connections),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun isValidate(): Boolean {
        var isValid = true
        if (binding.edtSearch.text.toString().trim().isEmpty()) {
            Toast.makeText(mContext, "Please enter keyword", Toast.LENGTH_SHORT)
                .show()
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

        viewModel.searchProductLiveData.observe(this, Observer {
            if (it.status == "success") {
                searchList.clear()
                searchList.addAll(it.data)
                if (searchList.size > 0) {
                    binding.tvNoData.visibility = View.GONE
                    binding.rvProducts.visibility = View.VISIBLE
                    setProductData(searchList)
                } else {
                    binding.tvNoData.visibility = View.VISIBLE
                    binding.rvProducts.visibility = View.GONE
                }
            } else {
                binding.tvNoData.visibility = View.VISIBLE
                binding.rvProducts.visibility = View.GONE

            }
        })
    }

    private fun setProductData(productModelList: ArrayList<SearchModel>) {
        binding.apply {
            rvProducts.layoutManager = GridLayoutManager(mContext, 2)
            rvProducts.setHasFixedSize(true)
            adapter = SearchAdapter(mContext, productModelList, this@SearchActivity)
            rvProducts.adapter = adapter
            adapter?.notifyDataSetChanged()
        }
    }

    override fun onClick(position: Int, model: SearchModel) {
        startActivity(
            Intent(mContext, ProductDetailActivity::class.java)
                .putExtra("mFrom", "isAdd")
                .putExtra("id", model.id.toString())
        )
    }
}