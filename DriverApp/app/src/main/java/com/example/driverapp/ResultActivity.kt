package com.example.driverapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.view.animation.AnimationUtils
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*

import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.math.max

class ResultActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var db: AppRoomDatabase
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    lateinit var item: Item


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        job = Job()
        db = Room.databaseBuilder(applicationContext, AppRoomDatabase::class.java, "Shopping")
            .fallbackToDestructiveMigration()
            .build()

        val correctAnswer = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)
        val total_Questions = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)
        score_id.text = "Your score is $correctAnswer out of $total_Questions"


        val bild1 = AnimationUtils.loadAnimation(this, R.anim.stb)
        val bild2 = AnimationUtils.loadAnimation(this, R.anim.bild2)
        val bild3 = AnimationUtils.loadAnimation(this, R.anim.bild3)
        val bild1id = findViewById<ImageView>(R.id.flag)
        val bild2id = findViewById<ImageView>(R.id.satilit)
        val bild3id = findViewById<ImageView>(R.id.star)
        bild1id.startAnimation(bild1)
        bild2id.startAnimation(bild2)
        bild3id.startAnimation(bild3)

//Hello
        val list = getAllItem()
        val SaveScore: MutableList<Int> = mutableListOf(0)
        val SavedTest: MutableList<Int> = mutableListOf(0)

        launch {
            val itemList = list.await()

            for (item in itemList) {
                SaveScore.add(item.TotalCorrect)
                SavedTest.add(item.TotalTest)
            }

            Log.d("!!!", "Super Score ${SaveScore.max()} ")
            Log.d("!!!", "Super Test ${SavedTest.max()!! + 1} ")
            Log.d("!!!", "Check " + SavedTest.size)
            item = Item(0, correctAnswer, SavedTest.size)

        }


        val intentMain = Intent(this, MainActivity::class.java)
        intentMain.putExtra(Constants.CORRECT_ANSWERS, correctAnswer)






        result_bt.setOnClickListener {

            val MaxSaved = SaveScore.max()
            if (correctAnswer > MaxSaved!!) {
                Log.d("!!!", "Is more")
                saveItem(item)
            }else{
                item = Item(0, 0, SavedTest.size)
                saveItem(item)
            }


            startActivity(intentMain)
        }


    }


    fun saveItem(item: Item) {

        launch(Dispatchers.IO) {
            db.itemDao().insert(item)
        }

    }

    fun getAllItem(): Deferred<List<Item>> =
        async(Dispatchers.IO) {
            db.itemDao().getAll()
        }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}