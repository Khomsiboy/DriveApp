package com.example.app2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.ImageView

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dogImage = findViewById<ImageView>(R.id.imageView)

        val flipImage = findViewById<FlipImageView>(R.id.catImage)
        flipImage.onDownImage = R.drawable.dog

        dogImage.setOnTouchListener{ v, event ->
            when(event?.action){
                MotionEvent.ACTION_DOWN ->{
                    Log.d("!!!","Down")
                    dogImage.setImageResource(R.drawable.cat)
                }
                MotionEvent.ACTION_UP ->{
                    Log.d("!!!","Up")
                    dogImage.setImageResource(R.drawable.dog)
                }
            }
            true
        }
    }
/*
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)

        while (event?.action){
            MotionEvent.ACTION_DOWN ->{

            }
        }

    }
   */

}