package com.example.sqlitedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private val personDao by lazy { PersonDatabase.getDatabase(this).personDao() }

    private lateinit var etName: EditText
    private lateinit var etLocation: EditText
    private lateinit var btSave: Button
    private lateinit var btRead: Button
    private lateinit var btUpdate: Button
    private lateinit var btDelete: Button

    private lateinit var rvMain: RecyclerView
    private lateinit var rvAdapter: RVAdapter

    private lateinit var people: List<Person>

    var selectedPerson: Person? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        people = arrayListOf()

        etName = findViewById(R.id.etName)
        etLocation = findViewById(R.id.etLocation)
        btSave = findViewById(R.id.btSave)
        btSave.setOnClickListener {
            val name = etName.text.toString()
            val location = etLocation.text.toString()
            CoroutineScope(IO).launch {
                personDao.addPerson(Person(0, name, location))
            }
            Toast.makeText(this, "Added successfully", Toast.LENGTH_LONG).show()
        }
        btRead = findViewById(R.id.btRead)
        btRead.setOnClickListener {
            CoroutineScope(IO).launch {
                val data = async {
                    personDao.getPeople()
                }.await()
                if(data.isNotEmpty()){
                    people = data
                    withContext(Main){
                        rvAdapter.update(people)
                    }
                }else{
                    Log.e("MainActivity", "Unable to get data", )
                }
            }
        }
        btUpdate = findViewById(R.id.btUpdate)
        btUpdate.setOnClickListener {
            if(selectedPerson != null){
                CoroutineScope(IO).launch {
                    val name = etName.text.toString()
                    val location = etLocation.text.toString()
                    personDao.updatePerson(Person(selectedPerson!!.pk, name, location))
                }
                Toast.makeText(this, "Updated successfully", Toast.LENGTH_LONG).show()
            }
        }
        btDelete = findViewById(R.id.btDelete)
        btDelete.setOnClickListener {
            if(selectedPerson != null){
                CoroutineScope(IO).launch {
                    personDao.deletePerson(selectedPerson!!)
                }
                Toast.makeText(this, "Deleted", Toast.LENGTH_LONG).show()
            }
        }

        rvMain = findViewById(R.id.rvMain)
        rvAdapter = RVAdapter(this)
        rvMain.adapter = rvAdapter
        rvMain.layoutManager = LinearLayoutManager(this)
    }

    fun updateFields(){
        etName.setText(selectedPerson!!.name)
        etLocation.setText(selectedPerson!!.location)
    }
}