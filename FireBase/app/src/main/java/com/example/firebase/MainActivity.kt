package com.example.firebase

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {


    val date = SimpleDateFormat("dd-MM-yyyy hh:mm a")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //November 4


       // val item = Item("Morrot",false,"Kyl")

       // val db = FirebaseFirestore.getInstance()// referance as a singleton
        //db.collection("item").add(item)


        clickLisner()



        var totalEmpHrs = 0
        var totalEmpMins = 0
        lateinit var startDate : String
        lateinit var endDate : String
        lateinit var startDateSubstring:String
        val clientName = "Hedera" //this hard coded name has to be replaced with selected customer name from recycler view
        endDate = "2020-11-27" /*this hard coded date has to be replaced with selected date text view value*/
        startDateSubstring = endDate.substring(0,8)
        Log.d("!!!","StartDate: $startDateSubstring")
        startDate = startDateSubstring + "01"
        Log.d("!!!","Start date value : $startDate")
        if (clientName != null) {
            fetchEmployeesForClients(clientName)
        }
        /*this function will do the work of pulling employees data from DB based on selected client name, selected date..
       Right now selected date value is hard coded. Need to use variable value instead of hard coded date value */
        fun fetchEmployeesForClients(clientName:String)
        {
            lateinit var clientDate: String
            lateinit var selEmpClientHrs: String
            lateinit var selEmpClientMins: String
            lateinit var docClientUID:String
            Log.d("!!!", "Inside fetch Employees function")
            //val empCollectionRef = fStore.collection("users").document()
            val clientCollection = fStore.collectionGroup("timereports")
                .whereEqualTo("clientname",clientName)
                .whereGreaterThanOrEqualTo("date",startDate)
                .whereLessThanOrEqualTo("date",endDate)
                .get()
                .addOnSuccessListener {snapshot->
                    Log.d("!!!","Document of employees: ${snapshot.documents}")
                    for(snap in snapshot)
                    {
                        docClientUID = snap.get("uid").toString()
                        // Log.d("!!!","Uid inside client document: $docClientUID")
                        val cRef1 = fStore.collection("users")
                            .document(docClientUID)
                            .get()
                            .addOnSuccessListener { documents ->
                                Log.d("!!!","Employees worked for selected client details:")
                                Log.d("!!!", snap.data.get("date").toString() + " -- " + documents.data.toString())
                                selEmpClientHrs = documents.data?.get("selectedHours").toString()
                                selEmpClientMins = documents.data?.get("selectedMinutes").toString()
                                totalEmpHrs = totalEmpHrs + selEmpClientHrs.toInt()
                                totalEmpMins = totalEmpMins + selEmpClientMins.toInt()
                                Log.d("!!!", "EmployeeHrs: ${selEmpClientHrs}")
                                Log.d("!!!", "EmployeeMins: ${selEmpClientMins}")
                                Log.d("!!!","Total Employee Hrs: $totalEmpHrs")
                                Log.d("!!!","Total Employee Minutes: $totalEmpMins")
                            }
                        if(totalEmpMins > 60)
                        {
                            Log.d("!!!"," Initial Total Hours ${totalEmpHrs}, Total Minutes ${totalEmpMins}")
                            var tmpHours = (totalEmpMins/60).toInt()
                            totalEmpHrs += tmpHours
                            totalEmpMins = totalEmpMins - (tmpHours * 60)
                            Log.d("!!!"," Total Hours ${totalEmpHrs}, Total Minutes ${totalEmpMins}")
                        }
                    }
                }
                .addOnFailureListener {e ->
                    Log.d("!!!", "Exception - ${e.message}")
                }
        }






        /// november 3

        //val db = FirebaseFirestore.getInstance()

        //val UsersCollections = mutableListOf<USData>()

      /*  val usersData = db.collection("users")
        usersData.get().addOnSuccessListener { documentSnapshot ->
             for (document in documentSnapshot.documents){
                 val newUsers = document.toObject(USData::class.java)
                 if (newUsers != null){
                     UsersCollections.add(newUsers)
                     Log.d("!!!" ,"{$newUsers}")
                 }
                 Log.d("!!!","{${UsersCollections.size}}")
             }

        }*/

        // Create a new user with a first and last name
        // Create a new user with a first and last name
       // val user: MutableMap<String, Any> = HashMap()
        //user["first"] = "Ada"
       // user["last"] = "Lovelace"
       // user["born"] = 1815

// Add a new document with a generated ID

// Add a new document with a generated ID
      //  db.collection("users")
        //        .add(user)
        //        .addOnSuccessListener { documentReference -> Log.d("!!!", "DocumentSnapshot added with ID: " + documentReference.id) }
         //       .addOnFailureListener { e -> Log.w("!!!", "Error adding document", e) }

    }

    private fun clickLisner() {

        button.setOnClickListener{
            Log.d("!!!","Click")
            pdf()
        }

    }

    private fun pdf() {
        val documentPdf = PdfDocument()
        val paint = Paint()
        val forlinePain = Paint()
        val pageInfo = PdfDocument.PageInfo.Builder(250,250,1).create()
        val page = documentPdf.startPage(pageInfo)

        val canvas = page.canvas

       paint.textSize = 15.5f
        paint.setColor(Color.BLACK)
        canvas.drawText("Hedera Helix",20f,20f,paint)
        paint.textSize = 9.5f
        canvas.drawText("Time report",20f,40f,paint)
        paint.textSize = 9.5f
        canvas.drawText("Date : " + " ${date.format(Date().time)}",20f,55f,paint)
        forlinePain.style = Paint.Style.STROKE
        forlinePain.pathEffect = DashPathEffect(floatArrayOf(5f,5f),5f)
        forlinePain.strokeWidth = 2f
        canvas.drawLine(20f,65f,230f,65f,forlinePain)

        documentPdf.finishPage(page)
        val file = File(this.getExternalFilesDir("/"),"Hedera Helix.pdf")

           try {
                documentPdf.writeTo(FileOutputStream(file))
           }catch (e : IOException){
               e.printStackTrace()
           }

        documentPdf.close()

    }
}