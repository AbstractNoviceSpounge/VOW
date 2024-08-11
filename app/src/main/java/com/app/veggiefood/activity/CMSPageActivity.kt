package com.app.veggiefood.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.app.veggiefood.R
import com.app.veggiefood.databinding.ActivityCmspageBinding
import com.app.veggiefood.extensions.isNetworkAvailable
import com.app.veggiefood.viewmodel.CMSPageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CMSPageActivity : AppCompatActivity() {

    lateinit var binding: ActivityCmspageBinding
    lateinit var mContext: CMSPageActivity
    private val viewModel: CMSPageViewModel by viewModels()
    private var type: String? = ""
    private var name: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cmspage)
        mContext = this
        initUI()
        addListner()
        addObsereves()
    }

    private fun initUI() {
        if (intent.extras != null) {
            type = intent.getStringExtra("type")
            name = intent.getStringExtra("name")
            binding.tvTitle.text = name
        }

        if (isNetworkAvailable(mContext)) {
            viewModel.getCMSPage(type.toString())
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
    }

    private fun addObsereves() {
        viewModel.isLoading.observe(this, Observer {
            if (it) {
                binding.pbLoadData.visibility = View.VISIBLE
            } else {
                binding.pbLoadData.visibility = View.GONE
            }
        })

        viewModel.cmsPageLiveData.observe(this, Observer {
            if (it.status) {
                binding.tvName.text = it.content
            }
        })
    }
}