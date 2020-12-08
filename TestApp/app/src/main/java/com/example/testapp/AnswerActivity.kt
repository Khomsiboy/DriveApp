package com.example.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class AnswerActivity : AppCompatActivity() {

    lateinit var text : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)

        val answer = intent.getBooleanExtra("theAnswer",false)

        text = findViewById(R.id.textView)

        text.text = if (answer)
            "Rätt"
        else
            "Fel"


        Log.d("!!!",answer.toString())
    }



}