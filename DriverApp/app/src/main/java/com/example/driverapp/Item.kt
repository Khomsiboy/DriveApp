package com.example.driverapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "TotalCorrect") var TotalCorrect:Int,
    @ColumnInfo(name = "TotalTest") var TotalTest:Int
)