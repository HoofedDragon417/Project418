package com.example.project418.adapters.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project418.R
import com.example.project418.databinding.RecyclerSubjectBinding
import com.example.project418.models.Subject

class SubjectAdapter(private val context: Context, private val items: List<Subject>) :
    RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder>() {
    inner class SubjectViewHolder(private val binding: RecyclerSubjectBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val item = items[position]

            binding.tvSubject.text = context.getString(R.string.subject, item.title)
            binding.tvTypeOfWork.text = context.getString(R.string.type_of_work, item.typeOfWork)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val itemView = RecyclerSubjectBinding.inflate(LayoutInflater.from(context), parent, false)
        return SubjectViewHolder(itemView)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) = holder.bind(position)
}