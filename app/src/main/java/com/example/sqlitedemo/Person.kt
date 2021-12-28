package com.example.sqlitedemo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Person(
    @PrimaryKey(autoGenerate = true) val pk: Int,
    val name: String,
    val location: String)