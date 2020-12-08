package com.example.driverapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity: AppCompatActivity(), CoroutineScope {


    private lateinit var db: AppRoomDatabase
    private lateinit var job : Job
    override val coroutineContext: CoroutineContext
    get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        job = Job()
        db = Room.databaseBuilder(applicationContext, AppRoomDatabase::class.java, "Shopping")
            .fallbackToDestructiveMigration()
            .build()

        start.setOnClickListener {
            val intent = Intent(this, Quiz_Activity::class.java)
            startActivity(intent)
            finish()
        }


       //val item = Item(0, Constants.TotalCorrectMain,Constants.TotalTestMain)





        val list = getAllItem()
        val SavedScored:MutableList<Int> = mutableListOf(0)
        val SavedTest:MutableList<Int> = mutableListOf(0)

        launch {
            val itemList = list.await()
            for (item in itemList) {
                SavedScored.add(item.TotalCorrect)
                SavedTest.add(item.TotalTest)
                Log.d("!!!", "${item.TotalCorrect}")


            }
            val showSavedScore = SavedScored.max()
            val showSavedTest = SavedTest.max()
            TotalScore.text = showSavedScore.toString()
            totalTest.text = showSavedTest.toString()

        }


        val CorrectAnswer = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)

        //Constants.TotalCorrect += CorrectAnswer
        //TotalScore.text = Constants.TotalCorrect.toString()

        //totalTest.text = Constants.TotalTest.toString()

        val ttb = AnimationUtils.loadAnimation(this, R.anim.ttb)
        val stb = AnimationUtils.loadAnimation(this, R.anim.stb)

        val R1 = AnimationUtils.loadAnimation(this, R.anim.r1)
        val R2 = AnimationUtils.loadAnimation(this, R.anim.r2)
        val R3 = AnimationUtils.loadAnimation(this, R.anim.r3)

        val SB = AnimationUtils.loadAnimation(this, R.anim.bt_start)

        val startB = findViewById<Button>(R.id.start)

        val header1 = findViewById(R.id.header1) as TextView
        val hearder2 = findViewById<TextView>(R.id.header2)

        val image = findViewById<ImageView>(R.id.bild)

        val r1 = findViewById<LinearLayout>(R.id.R1)
        val r2 = findViewById<LinearLayout>(R.id.R2)
        val r3 = findViewById<LinearLayout>(R.id.R3)


        // SET ANUMATION
        header1.startAnimation(ttb)
        header2.startAnimation(ttb)
        image.startAnimation(stb)
        r1.startAnimation(R1)
        r2.startAnimation(R2)
        r3.startAnimation(R3)
        startB.startAnimation(SB)
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

    fun getByCategory(category:String) = async(Dispatchers.IO) {
       // db.itemDao().findByCategory(category)
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

}