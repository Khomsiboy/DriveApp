package com.example.app2

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import java.util.jar.Attributes

class FlipImageView @JvmOverloads constructor(context:Context, attrs:AttributeSet? = null, defstyleAttr: Int = 0, var onDownImage : Int? = null,var defaultImage : Drawable? = null) : AppCompatImageView(context, attrs ,defstyleAttr){


    init {
        defaultImage = drawable
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {


        when(event?.action){
            MotionEvent.ACTION_DOWN ->{
                Log.d("!!!","Down")
                if (onDownImage != null)
                setImageResource(onDownImage!!)
            }
            MotionEvent.ACTION_UP ->{
                Log.d("!!!","Up")
                if (defaultImage != null)
                setImageDrawable(defaultImage)
            }

        }
        return true
    }



}