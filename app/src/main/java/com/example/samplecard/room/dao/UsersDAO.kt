package com.example.samplecard.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.samplecard.room.entity.UserEntity

@Dao
interface UsersDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveBooks(book: UserEntity)

    @Query(value = "Select * from todo_items")
    fun getAllBooks(): List<UserEntity>
}