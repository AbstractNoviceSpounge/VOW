package com.app.veggiefood.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.app.veggiefood.R
import com.app.veggiefood.adapter.BannerAdapter
import com.app.veggiefood.adapter.CustomArrayAdapter
import com.app.veggiefood.adapter.ProductAdapter
import com.app.veggiefood.databinding.ActivityMainBinding
import com.app.veggiefood.extensions.isNetworkAvailable
import com.app.veggiefood.model.request.CartRequest
import com.app.veggiefood.model.response.BannerModel
import com.app.veggiefood.model.response.CategoryModel
import com.app.veggiefood.model.response.ProductModel
import com.app.veggiefood.util.PrefManager
import com.app.veggiefood.util.StaticData
import com.app.veggiefood.viewmodel.BannerViewModel
import com.app.veggiefood.viewmodel.CategoryViewModel
import com.app.veggiefood.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Timer
import java.util.TimerTask

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ProductAdapter.onItemClick {

    lateinit var binding: ActivityMainBinding
    lateinit var mContext: MainActivity
    private var actionBarDrawerToggle: ActionBarDrawerToggle? = null
    private val viewModel: CategoryViewModel by viewModels()
    private var categoryModelList: ArrayList<CategoryModel> = arrayListOf()
    private var stringCategoryList: ArrayList<String> = arrayListOf()
    private var categoryId: String? = ""
    private val productViewModel: ProductViewModel by viewModels()
    private var productModelList: ArrayList<ProductModel> = arrayListOf()
    private val bannerViewModel: BannerViewModel by viewModels()
    private val stringSliderModel: ArrayList<String> = arrayListOf()
    private val sliderModel: ArrayList<BannerModel> = arrayListOf()
    private var size = 0

    private var swipeTimer: Timer? = null
    private var handler: Handler? = null
    private var Update: Runnable? = null
    private var currentPage = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mContext = this
        initUI()
        addListner()
        addObsereves()
    }

    private fun initUI() {

        setNavigationDrawer()

        if (isNetworkAvailable(mContext)) {
            bannerViewModel.getBanner()
            viewModel.getCategory()
        } else {
            Toast.makeText(
                mContext, getString(R.string.str_error_internet_connections),
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    private fun addListner() {
        binding.ivMenu.setOnClickListener {
            binding.drawerlayout.openDrawer(GravityCompat.START)
        }

        binding.cart.setOnClickListener {
            startActivity(Intent(mContext, MyCartActivity::class.java))
        }

        binding.ivSearch.setOnClickListener {
            startActivity(Intent(mContext, SearchActivity::class.java))
        }

        binding.ivNotifications.setOnClickListener {
            startActivity(Intent(mContext, NotificationActivity::class.java))
        }

        binding.acCategory.setOnTouchListener { v, event ->
            binding.acCategory.showDropDown()
            false
        }

        binding.pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(i: Int, v: Float, i1: Int) {}
            override fun onPageSelected(position: Int) {
                addMarkers(
                    position,
                    stringSliderModel,
                    binding.layoutMarkers,
                    mContext
                )
            }

            override fun onPageScrollStateChanged(i: Int) {}
        })

    }

    override fun onResume() {
        super.onResume()
        if (isNetworkAvailable(mContext)) {
            productViewModel.totalCount(
                CartRequest(PrefManager(mContext).getvalue(StaticData.id).toString())
            )
        } else {
            Toast.makeText(
                mContext, getString(R.string.str_error_internet_connections),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun addMarkers(
        currentPage: Int,
        imglist: ArrayList<String>,
        mMarkersLayout: LinearLayout,
        context: Context?
    ) {
        if (context != null) {
            val markers = arrayOfNulls<TextView>(imglist.size)
            mMarkersLayout.removeAllViews()
            for (i in markers.indices) {
                markers[i] = TextView(context)
                markers[i]!!.text = Html.fromHtml("&#8226;")
                markers[i]!!.textSize = 35f
                markers[i]!!.setTextColor(context.resources.getColor(R.color.grey))
                mMarkersLayout.addView(markers[i])
            }
            if (markers.size > 0) markers[currentPage]!!.setTextColor(context.resources.getColor(R.color.pinkcolor))
        }
    }


    private fun setNavigationDrawer() {
        binding.navigationview.bringToFront()
        binding.navigationview.setNavigationItemSelectedListener { item ->
            val id = item.itemId
            when (id) {
                R.id.menu_home -> {
                    startActivity(Intent(mContext, MainActivity::class.java))
                }

                R.id.menu_changepassword -> {
                    startActivity(Intent(mContext, ChangePasswordActivity::class.java))
                }

                R.id.menu_profile -> {
                    startActivity(Intent(mContext, MyProfileActivity::class.java))

                }

                R.id.menu_orderhistory -> {
                    startActivity(Intent(mContext, OrderHistoryActivity::class.java))
                }

                R.id.menu_trackorder -> {
                    startActivity(Intent(mContext, TrackOrderActivity::class.java))
                }

                R.id.menu_aboutus -> {
                    startActivity(
                        Intent(mContext, CMSPageActivity::class.java)
                            .putExtra("type", "about-us")
                            .putExtra("name", "AboutUs")
                    )
                }

                R.id.menu_termsconditions -> {
                    startActivity(
                        Intent(mContext, CMSPageActivity::class.java)
                            .putExtra("type", "terms-condition")
                            .putExtra("name", "Terms & Conditions")
                    )
                }

                R.id.menu_mycart -> {
                    startActivity(Intent(mContext, MyCartActivity::class.java))
                }
                R.id.menu_share->{
                    try {
                        val shareIntent = Intent(Intent.ACTION_SEND)
                        shareIntent.type = "text/plain"
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "")
                        var shareMessage = "\nLet me recommend you this application\n\n"
                        shareMessage =
                            """
                            ${shareMessage + "https://play.google.com/store/apps/details?id=" + packageName}
                            
                            
                            """.trimIndent()
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                        startActivity(Intent.createChooser(shareIntent, "choose one"))
                    } catch (e: Exception) {
                        //e.toString();
                    }

                }

                R.id.menu_logout -> {
                    logoutDialog()
                }

            }
            binding.drawerlayout.closeDrawer(GravityCompat.START)
            true
        }
        actionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            binding.drawerlayout,
            null,
            R.string.open_drawer,
            R.string.close_drawer
        ) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                binding.drawerlayout.openDrawer(GravityCompat.START)
                setHeaderData()
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                binding.drawerlayout.closeDrawer(GravityCompat.START)
            }
        }
        binding.drawerlayout.setDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle?.syncState()
    }

    @SuppressLint("SetTextI18n")
    private fun setHeaderData() {
        val hView = binding.navigationview.getHeaderView(0)
        val cvProfile = hView.findViewById<ImageView>(R.id.ivImage)
        val name = hView.findViewById<TextView>(R.id.tvName)
        /*
                if (PrefManager(mContext).getvalue(StaticData.image)?.isNotEmpty() == true) {
                    Glide.with(mContext)
                        .load(PrefManager(mContext).getvalue(StaticData.image))
                        .placeholder(R.drawable.ic_profile).error(R.drawable.ic_profile)
                        .into(cvProfile)
                }
        */

        name.text = PrefManager(mContext).getvalue(StaticData.fname) + " " + PrefManager(
            mContext
        ).getvalue(StaticData.lname)

        val tvVersion = hView.findViewById<TextView>(R.id.tvAppVersion)
        tvVersion.text = "Version:- " + "1.0"
    }

    private fun logoutDialog() {
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Logout")
        builder.setMessage("Are you sure you want to Logout?")

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->

            PrefManager(mContext).clearValue()
            startActivity(Intent(mContext, LoginActivity::class.java))
            finishAffinity()

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

        viewModel.categoryLiveData.observe(this, Observer {
            if (it.status) {
                categoryModelList.clear()
                categoryModelList.addAll(it.data)
                setCategoryData(categoryModelList)
            } else {

            }
        })

        productViewModel.totalCountLiveData.observe(this, Observer {
            if (it.status == "success") {
                if (it.data.item_count == "0") {
                    binding.cartBadge.visibility = View.GONE
                } else {
                    binding.cartBadge.text = it.data.item_count
                    binding.cartBadge.visibility = View.VISIBLE
                }
            } else {

            }
        })

        bannerViewModel.isBannerLiveData.observe(this, Observer {
            if (it.status) {
                binding.rrBanner.visibility = View.VISIBLE
                sliderModel.clear()
                stringSliderModel.clear()
                sliderModel.addAll(it.data)
                setSliderData(sliderModel)
                size = it.data.size

                //   setSliderData(sliderArrayList);
                binding.pager.setAdapter(
                    BannerAdapter(
                        stringSliderModel,
                        mContext,
                        R.layout.lyt_slider,
                    )
                )
                addMarkers(
                    0,
                    stringSliderModel,
                    binding.layoutMarkers,
                    mContext
                )
                handler = Handler()
                Update = Runnable {
                    if (currentPage == size) {
                        currentPage = 0
                    }
                    try {
                        binding.pager.setCurrentItem(currentPage++, true)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                swipeTimer = Timer()
                swipeTimer!!.schedule(object : TimerTask() {
                    override fun run() {
                        handler!!.post(Update!!)
                    }
                }, 3000, 3000)

            } else {
                binding.rrBanner.visibility = View.GONE
            }
        })

        productViewModel.productLiveData.observe(this, Observer {
            if (it.status) {
                productModelList.clear()
                productModelList.addAll(it.data)
                binding.tvNoData.visibility = View.GONE
                binding.rvProducts.visibility = View.VISIBLE
                setProductData(productModelList)
            } else {
                binding.tvNoData.visibility = View.VISIBLE
                binding.rvProducts.visibility = View.GONE
            }
        })
    }

    private fun setSliderData(sliderModel: ArrayList<BannerModel>) {
        for (i in 0..<sliderModel.size) {
            stringSliderModel.add(sliderModel[i].image_url)
        }
    }

    private fun setProductData(productModelList: ArrayList<ProductModel>) {
        binding.apply {
            rvProducts.layoutManager = GridLayoutManager(mContext, 2)
            rvProducts.setHasFixedSize(true)
            adapter = ProductAdapter(mContext, productModelList, this@MainActivity)
            rvProducts.adapter = adapter
            adapter?.notifyDataSetChanged()
        }
    }

    private fun setCategoryData(data: ArrayList<CategoryModel>) {
        stringCategoryList.clear()
        for (i in 0 until data.size) {
            stringCategoryList.add(data[i].name)
        }

        binding.acCategory.setText(data[0].name)

        productViewModel.getProduct(data[0].id)


        val arrayAdapter =
            CustomArrayAdapter(mContext, stringCategoryList)
        binding.acCategory.threshold = 0
        binding.acCategory.dropDownVerticalOffset = 0
        binding.acCategory.setAdapter(arrayAdapter)

        binding.acCategory.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                categoryId = data[position].id

                if (isNetworkAvailable(mContext)) {
                    mNetworkCallProductAPI(categoryId.toString())
                } else {
                    Toast.makeText(
                        mContext, getString(R.string.str_error_internet_connections),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun mNetworkCallProductAPI(categoryId: String) {
        productViewModel.getProduct(categoryId)
    }

    override fun onClick(position: Int, model: ProductModel) {
        startActivity(
            Intent(mContext, ProductDetailActivity::class.java)
                .putExtra("mFrom", "isAdd")
                .putExtra("id", model.id.toString())
        )
    }
}