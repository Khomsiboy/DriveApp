package com.example.driverapp

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Item::class), version = 7)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun itemDao() : ItemDao

}