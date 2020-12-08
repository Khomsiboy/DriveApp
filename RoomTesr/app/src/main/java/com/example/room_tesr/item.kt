package com.example.room_tesr

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item(@PrimaryKey(autoGenerate = true) val id:Int,
                @ColumnInfo(name = "Name") var name:String? = null,
                @ColumnInfo(name = "Done")var done:Boolean = false,
                @ColumnInfo(name = "CategoryName")var category:String? = null) {


}