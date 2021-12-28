package com.example.sqlitedemo

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlitedemo.databinding.ItemRowBinding

class RVAdapter(private val activity: MainActivity):RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {
    private var people = emptyList<Person>()

    class ItemViewHolder(val binding: ItemRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val person = people[position]

        holder.binding.apply {
            val personData = "${person.pk} - ${person.name} - ${person.location}"
            tvPerson.text = personData
            tvPerson.setOnClickListener {
                activity.selectedPerson = person
                activity.updateFields()
                Toast.makeText(activity, "${person.name}(${person.pk}) selected", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun getItemCount() = people.size

    fun update(people: List<Person>){
        this.people = people
        notifyDataSetChanged()
    }
}