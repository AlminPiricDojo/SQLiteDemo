package com.example.sqlitedemo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context): SQLiteOpenHelper(context,"details.db", null, 2) {
    private val sqLiteDatabase: SQLiteDatabase = writableDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        if(db != null){
            db.execSQL("create table students (pk INTEGER PRIMARY KEY AUTOINCREMENT, Name text, Location text)")
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        sqLiteDatabase!!.execSQL("DROP TABLE IF EXISTS students")  // This removes the table if a new version is detected
        onCreate(sqLiteDatabase)
    }

    fun saveData(name: String, location: String){
        val contentValues = ContentValues()
        contentValues.put("Name", name)
        contentValues.put("Location", location)
        sqLiteDatabase.insert("students", null, contentValues)
    }

    fun readData(): ArrayList<Person>{
        val people = arrayListOf<Person>()

        // Read all data using cursor
        val cursor: Cursor = sqLiteDatabase.rawQuery("SELECT * FROM students", null)

        if(cursor.count < 1){  // Handle empty table
            println("No Data Found")
        }else{
            while(cursor.moveToNext()){  // Iterate through table and populate people Array List
                val pk = cursor.getInt(0)  // The integer value refers to the column
                val name = cursor.getString(1)
                val location = cursor.getString(2)
                people.add(Person(pk, name, location))
            }
        }
        return people
    }
}