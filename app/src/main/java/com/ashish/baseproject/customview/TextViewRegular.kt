package com.ashish.baseproject.customview

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet

import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatTextView
import com.ashish.baseproject.util.Constants


class TextViewRegular : AppCompatTextView {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init()
    }

    private fun init() {
        this.isInEditMode
        val tf = Typeface.createFromAsset(context.assets, Constants.font_name)
        typeface = tf
    }

    companion object {


        /**
         *      * Below Static method is Added for the uses of Drawable Left/Right/Start/End in Textview or Editext in Xml
         *      *  */

        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }

        private val TAG = "TextView"
    }

}
