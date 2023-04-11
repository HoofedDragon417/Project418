package com.example.project418.adapters.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project418.R
import com.example.project418.databinding.RecyclerJournalBinding
import com.example.project418.models.Journal
import java.text.SimpleDateFormat
import java.util.*

class JournalAdapter(private val context: Context, private val items: List<Journal>) :
    RecyclerView.Adapter<JournalAdapter.JournalViewHOlder>() {
    inner class JournalViewHOlder(private val binding: RecyclerJournalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val item = items[position]

            val pattern = context.getString(R.string.registration_pattern)
            val sdf = SimpleDateFormat(pattern, Locale.getDefault())

            if (item.title != "-") binding.tvTitleOfWork.text =
                context.getString(R.string.title_recycler, item.title)
            binding.tvStudent.text = context.getString(R.string.student_recycler, item.student)
            binding.tvSubject.text = context.getString(R.string.subject_recycler, item.subject)
            binding.tvTypeOfWork.text =
                context.getString(R.string.type_of_work_recycler, item.typeOfWork)
            binding.tvTeacher.text = context.getString(R.string.teacher_recycler, item.teacher)
            binding.tvRegistrationData.text =
                context.getString(R.string.registration_date, sdf.format(item.registrationData))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JournalViewHOlder {
        val itemView = RecyclerJournalBinding.inflate(LayoutInflater.from(context), parent, false)
        return JournalViewHOlder(itemView)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: JournalViewHOlder, position: Int) = holder.bind(position)
}