package com.example.project418.adapters.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project418.R
import com.example.project418.databinding.RecyclerTeachersBinding
import com.example.project418.models.Teachers

class TeachersAdapter(private val context: Context, private val items: List<Teachers>) :
    RecyclerView.Adapter<TeachersAdapter.TeacherViewHolder>() {
    inner class TeacherViewHolder(private val binding: RecyclerTeachersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val item = items[position]
            binding.tvTeacherFioField.text = context.getString(
                R.string.fio,
                item.lastName,
                item.firstName,
                item.middleName
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherViewHolder {
        val itemView = RecyclerTeachersBinding.inflate(LayoutInflater.from(context), parent, false)
        return TeacherViewHolder(itemView)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: TeacherViewHolder, position: Int) = holder.bind(position)
}