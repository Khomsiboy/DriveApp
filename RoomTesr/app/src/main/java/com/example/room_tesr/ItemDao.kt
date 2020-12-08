package com.example.room_tesr

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao //Data Access Object
interface ItemDao {

    @Insert
    fun insert(item : Item)

    @Delete
    fun delete(item : Item)

    @Query("SELECT * FROM item")
    fun getAll() : List<Item>

    @Query("SELECT * FROM item WHERE CategoryName LIKE :categoryName")
    fun findByCategory(categoryName:String) : List<Item>

}