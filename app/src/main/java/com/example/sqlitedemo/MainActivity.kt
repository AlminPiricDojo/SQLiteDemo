package com.example.sqlitedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var etName: EditText
    private lateinit var etLocation: EditText
    private lateinit var btSave: Button
    private lateinit var btRead: Button
    private lateinit var btUpdate: Button
    private lateinit var btDelete: Button

    private lateinit var rvMain: RecyclerView
    private lateinit var rvAdapter: RVAdapter

    private lateinit var people: ArrayList<Person>

    var selectedPerson: Person? = null

    private val databaseHelper by lazy { DatabaseHelper(applicationContext) }

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
            databaseHelper.saveData(name, location)
            Toast.makeText(this, "Added successfully", Toast.LENGTH_LONG).show()
        }
        btRead = findViewById(R.id.btRead)
        btRead.setOnClickListener {
            people = databaseHelper.readData()
            rvAdapter.update(people)
        }
        btUpdate = findViewById(R.id.btUpdate)
        btUpdate.setOnClickListener {
            if(selectedPerson != null){
                val name = etName.text.toString()
                val location = etLocation.text.toString()
                databaseHelper.updateData(Person(selectedPerson!!.pk, name, location))
                Toast.makeText(this, "Updated successfully", Toast.LENGTH_LONG).show()
                selectedPerson = null
            }
        }
        btDelete = findViewById(R.id.btDelete)
        btDelete.setOnClickListener {
            if(selectedPerson != null){
                databaseHelper.deleteData(selectedPerson!!)
                Toast.makeText(this, "Deleted", Toast.LENGTH_LONG).show()
                selectedPerson = null
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