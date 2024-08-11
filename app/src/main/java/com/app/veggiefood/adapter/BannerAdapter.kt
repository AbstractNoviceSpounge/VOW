package com.app.veggiefood.adapter

import android.content.Context
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.app.veggiefood.R
import com.bumptech.glide.Glide

class BannerAdapter(
    var dataList: ArrayList<String>?,
    var activity: Context,
    var layout: Int
) :
    PagerAdapter() {
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageLayout = LayoutInflater.from(activity).inflate(layout, container, false)!!
        val imgslider = imageLayout.findViewById<ImageView>(R.id.imgslider)
        Glide.with(activity).load(dataList!![position]).into(imgslider)
        container.addView(imageLayout, 0)
        return imageLayout
    }

    override fun getCount(): Int {
        return if (null != dataList) dataList!!.size else 0
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun saveState(): Parcelable? {
        return null
    }
}
