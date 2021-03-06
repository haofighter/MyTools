package com.intro.hao.mytools.base

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.intro.hao.mytools.R
import com.intro.hao.mytools.Utils.SystemUtils
import com.intro.hao.mytools.customview.NavigationBar
import kotlinx.android.synthetic.main.activity_side_base.*
import kotlinx.android.synthetic.main.toolbar_base_activity.*


/**
 * Created by haozhang on 2018/1/9.
 */
abstract class BaseToolBarActivity : BaseDrawerActivity() {


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.toolbar_base_activity)
        initToolBar()
        initView()
    }

    abstract fun initView()

    abstract fun LayoutID(): Int

    lateinit var navigation: NavigationBar
    /**
     * 初始化界面
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun initToolBar() {
        base_content.setPadding(0, SystemUtils().getDecorViewHightByReflect(this), 0, 0)
        navigation = navigation_bar
        /**
         * 用于判断状态栏颜色 便于识别
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            var navBackGround = drawerLayout.background
            if (navBackGround is ColorDrawable) {
                if (navBackGround.color == -1) {
                    window.statusBarColor = ContextCompat.getColor(this, R.color.gray_44)
//                window.setStatusBarColor(this.getResources().getColor(R.color.black));
                }
            } else if (navBackGround is BitmapDrawable) {
                var bgBitmap = navBackGround.bitmap  //获取到了背景图片
                window.statusBarColor = ContextCompat.getColor(this, R.color.gray_44)
            } else {
                window.statusBarColor = ContextCompat.getColor(this, R.color.gray_44)
            }
        }
        if (LayoutID() != 0) {
            setToolsBaseContentView(LayoutID())
        }
    }

    /**
     * 自行填充界面
     */
    fun setToolsBaseContentView(layoutId: Int) {
        var view = LayoutInflater.from(this).inflate(layoutId, null)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        setToolsBaseContentView(view)
    }


    fun setToolsBaseContentView(layout: View) {
        tool_base_content.addView(layout)
    }
}