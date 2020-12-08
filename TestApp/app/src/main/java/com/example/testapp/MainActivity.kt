package com.example.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import android.util.Log as Log

class MainActivity : AppCompatActivity() {


    lateinit var questionText : TextView
    lateinit var answerText : EditText
     var firstN = 0
     var secondN = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        questionText = findViewById(R.id.questionText)
        answerText = findViewById(R.id.answerEdit)

        val button = findViewById<Button>(R.id.answerButton)

        button.setOnClickListener{
            startAnswerActivity()
        }


        randomizeQuestions()
    }

    fun checkAnswer() : Boolean{
        val answerText = answerEdit.text.toString()
        val answerToInt = answerText.toIntOrNull()

        return (firstN + secondN == answerToInt )


    }

    fun randomizeQuestions(){
         firstN = (1..20).random()
         secondN = (1..20).random()

        val question = "$firstN + $secondN"

        Log.d("!!!",question)

        questionText.text = question
    }

    fun startAnswerActivity(){

        val rightAnswer = checkAnswer()


        val intent = Intent(this,AnswerActivity::class.java)

        intent.putExtra("theAnswer",rightAnswer)

        startActivity(intent)
    }

    override fun onRestart() {
        super.onRestart()
        answerText.setText("")
        randomizeQuestions()
    }

}