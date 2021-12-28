package com.example.sqlitedemo

import androidx.room.*

@Dao
interface PersonDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPerson(person: Person)

    @Query("SELECT * FROM students ORDER BY pk ASC")
    fun getPeople(): List<Person>

    @Update
    suspend fun updatePerson(person: Person)

    @Delete
    suspend fun deletePerson(person: Person)
}