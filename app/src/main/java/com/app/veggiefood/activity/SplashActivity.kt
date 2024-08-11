package com.app.veggiefood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.databinding.DataBindingUtil
import com.app.veggiefood.R
import com.app.veggiefood.databinding.ActivitySplashBinding
import com.app.veggiefood.util.PrefManager
import com.app.veggiefood.util.StaticData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding
    lateinit var mContext: SplashActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        mContext = this


        Handler(Looper.getMainLooper()).postDelayed(
            {
                if (PrefManager(mContext).getvalue(StaticData.isLogin,false)){
                    startActivity(Intent(mContext,MainActivity::class.java))
                }else{
                    startActivity(Intent(mContext, LoginActivity::class.java))
                }
                finish()
            }, StaticData.SPLASH_TIME_OUT.toLong()
        )
    }
}