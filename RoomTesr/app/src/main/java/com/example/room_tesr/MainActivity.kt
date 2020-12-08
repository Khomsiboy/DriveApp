package com.example.room_tesr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.room.Room
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() , CoroutineScope {

    private lateinit var job :Job
    override val coroutineContext: CoroutineContext
    get() = Dispatchers.Main + job


    private lateinit var db : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        job = Job()

        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java,"Shopping-Item")
            .fallbackToDestructiveMigration()
            .build()

       // val item = Item(0,"smör", false,"Kyl")

       // saveItem(item)

        val LoadItem = getAllData()

        launch {
            val listItem = LoadItem.await()
            for(item in listItem){
                Log.d("!!!"," item : $item")
            }
        }



    }

    fun saveItem(item: Item) {

        launch(Dispatchers.IO) {
            db.itemDao().insert(item)
        }

    }
        fun getAllData() : Deferred<List<Item>> =
            async(Dispatchers.IO){
                db.itemDao().getAll()
            }


    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }


}