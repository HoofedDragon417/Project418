package com.example.project418.adapters.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project418.R
import com.example.project418.databinding.RecyclerStudentBinding
import com.example.project418.models.Student

class StudentAdapter(private val context: Context, private val items: List<Student>) :
    RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    inner class StudentViewHolder(private val binding: RecyclerStudentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val item = items[position]
            binding.tvStudentFio.text = context.getString(
                R.string.fio,
                item.lastName,
                item.firstName,
                item.middleName
            )
            binding.tvStudentGroup.text = context.getString(R.string.student_group, item.group)
            binding.tvStudentCourse.text = context.getString(R.string.student_course, item.course)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val itemView = RecyclerStudentBinding.inflate(LayoutInflater.from(context), parent, false)
        return StudentViewHolder(itemView)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) = holder.bind(position)
}