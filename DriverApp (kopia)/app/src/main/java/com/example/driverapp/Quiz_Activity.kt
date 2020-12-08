package com.example.driverapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quiz_.*
import kotlin.math.log

class Quiz_Activity : AppCompatActivity(), View.OnClickListener {

    
       private var mCurrentPosition:Int = 1
       private var mQuestionsList: ArrayList<Question>? = null
       private var mSlectedOptionPosition : Int = 0
       private var mCorrectAnswer: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN


        mQuestionsList = Constants.getQuestions()

        setQuestion()
        OP1.setOnClickListener(this)
        OP2.setOnClickListener(this)
        OP3.setOnClickListener(this)
        OP4.setOnClickListener(this)

        Submit_id.setOnClickListener(this)



    }


    private fun setQuestion(){


        val question = mQuestionsList!![mCurrentPosition - 1]

        defaultOptinView()
        if (mCurrentPosition == mQuestionsList!!.size)
            Submit_id.text = "FINISH"
        else
            Submit_id.text = "SUBMIT"

        progress_bar.progress = mCurrentPosition
        text_progress.text = "$mCurrentPosition" + "/" + progress_bar.max

        Question_ID.text = question!!.question
        Image_ID.setImageResource(question.image)
        OP1.text = question.optionOne
        OP2.text = question.optionTwo
        OP3.text = question.optionThree
        OP4.text = question.optionFour

    }

    private fun defaultOptinView(){

        val options = ArrayList<TextView>()
        options.add(0,OP1)
        options.add(1,OP2)
        options.add(2,OP3)
        options.add(3,OP4)


        for (optin in options){

            optin.setTextColor(Color.parseColor("#7A8089"))
            optin.typeface = Typeface.DEFAULT
            optin.background = ContextCompat.getDrawable(this,R.drawable.button_bc)
        }

    }

    override fun onClick(v: View?) {

        when(v?.id){
            R.id.OP1 ->{
                selectedOptionView(OP1,1)
            }
            R.id.OP2 ->{
                selectedOptionView(OP2,2)
            }
            R.id.OP3 ->{
                selectedOptionView(OP3,3)
            }
            R.id.OP4 ->{
                selectedOptionView(OP4,4)
            }
            R.id.Submit_id ->{
                if (mSlectedOptionPosition == 0){
                    mCurrentPosition++

                    when{
                        mCurrentPosition <= mQuestionsList!!.size ->{
                            setQuestion()
                        }else ->{

                            val intent = Intent(this,ResultActivity::class.java)
                            intent.putExtra(Constants.CORRECT_ANSWERS,mCorrectAnswer)
                            intent.putExtra(Constants.TOTAL_QUESTIONS,mQuestionsList!!.size)
                            startActivity(intent)
                            Toast.makeText(this,"You Completed the Quest",Toast.LENGTH_SHORT).show()
                       }
                    }

                }else{
                    val question = mQuestionsList?.get(mCurrentPosition -1)
                    if (question!!.correctAnswer != mSlectedOptionPosition){
                        answerView(mSlectedOptionPosition,R.drawable.false_bg)
                    }
                    else{
                        mCorrectAnswer++
                    }
                    answerView(question.correctAnswer,R.drawable.correct_bg)

                    if (mCurrentPosition == mQuestionsList!!.size)
                        Submit_id.text = "FINISH"
                    else
                        Submit_id.text = "NEXT QUESTION"

                    mSlectedOptionPosition = 0
                }
            }
        }

    }


    private fun answerView(answer: Int, drawView:Int){

        when(answer){
            1 ->{
                OP1.background = ContextCompat.getDrawable(this,drawView)
            }
            2 ->{
                OP2.background = ContextCompat.getDrawable(this,drawView)
            }
            3 ->{
                OP3.background = ContextCompat.getDrawable(this,drawView)
            }
            4 ->{
                OP4.background = ContextCompat.getDrawable(this,drawView)
            }
        }
    }

    private fun selectedOptionView(tv:TextView,selectedOPtionNum:Int){

        defaultOptinView()
        mSlectedOptionPosition = selectedOPtionNum
        tv.setTextColor(Color.parseColor("#7A8089"))
        tv.setTypeface(tv.typeface,Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this,R.drawable.option_bc)
    }

}