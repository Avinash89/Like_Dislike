package com.example.samplecard.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.samplecard.room.dao.UsersDAO
import com.example.samplecard.room.entity.UserEntity

@Database(entities = [(UserEntity::class)], version = 1)
abstract class AppDb : RoomDatabase() {

    abstract fun bookDao(): UsersDAO
}