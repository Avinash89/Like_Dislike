package com.example.samplecard.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_items")
data class UserEntity(
    @PrimaryKey()
    var id: String,

    @ColumnInfo(name = "user_name") var title: String,
    @ColumnInfo(name = "image_url") var content: String,
    @ColumnInfo(name = "status") var status: Boolean
)