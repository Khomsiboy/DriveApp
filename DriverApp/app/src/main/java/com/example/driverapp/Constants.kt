package com.example.driverapp

object Constants{



    const val USER_NAME: String = "User_Name"
    const val TOTAL_QUESTIONS: String = "Total_Questions"
    const val CORRECT_ANSWERS:String = "Correct_answers"


    var TotalCorrectMain:Int = 0
    var TotalTestMain:Int = 0


    fun getQuestions():ArrayList<Question>{
        val questionList = ArrayList<Question>()

        val que1 = Question(1,
            "När återkallar polisen normalt ditt körkort?",
            R.drawable.bild2,
            "Om jag smiter från en trafikolycka",
            "Om jag kör 35 km/h på en 30-väg",
            "Om jag kör över heldragen linje",
            "Vet inte",
            3)

        val que2 = Question(1,
            "Vilka fordon får färdas på cykelbanan?",
            R.drawable.bild3,
            "Endast cyklar",
            "Cyklar och moped klass I",
            "Cyklar och moped klass II",
            "Cyklar och moped klass I och II",
            3)

        val que3 = Question(1,
            "Du ska fortsätta köra rakt fram. Vem ska lämna företräde?",
            R.drawable.bild1,
            "Jag, enligt högerregeln",
            "Föraren i gråa bilen, enligt svängningsregeln",
            "Kör samtidigt ",
            "Vet inte",
            3)

        questionList.add(que1)
        questionList.add(que2)
        questionList.add(que3)
        return questionList
    }
}