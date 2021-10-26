package com.example.sqlitedemo

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val personDao by lazy { PersonDatabase.getDatabase(application).personDao() }
    private val people: MutableLiveData<List<Person>> = MutableLiveData()

    fun addPerson(name: String, location: String){
        CoroutineScope(Dispatchers.IO).launch {
            personDao.addPerson(Person(0, name, location))
            readData()
        }
    }

    fun getPeople(): MutableLiveData<List<Person>>{
        return people
    }

    fun readData(){
        CoroutineScope(Dispatchers.IO).launch {
            val data = async {
                personDao.getPeople()
            }.await()
            withContext(Main){
                if(data.isNotEmpty()){
                    people.postValue(data)
                }else{
                    Log.e("MainActivity", "Unable to get data", )
                }
            }
        }
    }

    fun updatePerson(pk: Int, name: String, location: String){
        CoroutineScope(Dispatchers.IO).launch {
            personDao.updatePerson(Person(pk, name, location))
            readData()
        }
    }

    fun deletePerson(person: Person){
        CoroutineScope(Dispatchers.IO).launch {
            personDao.deletePerson(person)
            readData()
        }
    }
}