package com.ashish.baseproject.util

import android.app.Activity
import android.view.View
import android.widget.ImageView
import com.ashish.baseproject.R
import com.ashish.baseproject.customview.TextViewRegular

class ToolBarUtil {
    constructor(activity: Activity, headTitle: String,showBackButton:Boolean) {
        val toolbar = activity.findViewById<androidx.appcompat.widget.Toolbar>(R.id.commonToolBar)
        toolbar.findViewById<ImageView>(R.id.toolbarBackButton)
            .setOnClickListener { activity.finish() }
        if(!showBackButton){
            toolbar.findViewById<ImageView>(R.id.toolbarBackButton).visibility= View.GONE
        }
        toolbar.findViewById<TextViewRegular>(R.id.tvToolbarText).text = headTitle
    }
}