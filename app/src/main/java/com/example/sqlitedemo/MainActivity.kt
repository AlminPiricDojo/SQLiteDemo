package com.example.sqlitedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
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

    var selectedPerson: Person? = null

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.getPeople().observe(this, {
            people -> rvAdapter.update(people)
        })

        etName = findViewById(R.id.etName)
        etLocation = findViewById(R.id.etLocation)
        btSave = findViewById(R.id.btSave)
        btSave.setOnClickListener {
            val name = etName.text.toString()
            val location = etLocation.text.toString()
            mainViewModel.addPerson(name, location)
            Toast.makeText(this, "Added successfully", Toast.LENGTH_LONG).show()
        }
        btRead = findViewById(R.id.btRead)
        btRead.setOnClickListener {
            mainViewModel.readData()
        }
        btUpdate = findViewById(R.id.btUpdate)
        btUpdate.setOnClickListener {
            if(selectedPerson != null){
                val name = etName.text.toString()
                val location = etLocation.text.toString()
                mainViewModel.updatePerson(selectedPerson!!.pk, name, location)
                Toast.makeText(this, "Updated successfully", Toast.LENGTH_LONG).show()
            }
        }
        btDelete = findViewById(R.id.btDelete)
        btDelete.setOnClickListener {
            if(selectedPerson != null){
                mainViewModel.deletePerson(selectedPerson!!)
                Toast.makeText(this, "Deleted", Toast.LENGTH_LONG).show()
            }
        }

        rvMain = findViewById(R.id.rvMain)
        rvAdapter = RVAdapter(this)
        rvMain.adapter = rvAdapter
        rvMain.layoutManager = LinearLayoutManager(this)
    }
}