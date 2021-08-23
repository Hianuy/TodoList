package com.hianuy.todolist.project.ui.adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hianuy.todolist.R
import com.hianuy.todolist.R.color.black
import com.hianuy.todolist.project.database.entity.Priority
import com.hianuy.todolist.project.database.entity.TaskEntity


class TaskAdapter(private val tasks: List<TaskEntity>) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    var onItemClick: ((entity: TaskEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
//        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            holder.bindView(tasks[position])
        }

    }

    override fun getItemCount() = tasks.size

    inner class TaskViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val txtTitle: TextView = itemView.findViewById(R.id.txt_title)
        private val txtDeadline: TextView = itemView.findViewById(R.id.txt_deadline)
        private val cardPriority: CardView = itemView.findViewById(R.id.card_priority)


        @SuppressLint("ResourceAsColor")
        @RequiresApi(Build.VERSION_CODES.O)
        fun bindView(task: TaskEntity) {
            txtTitle.text = task.title
            txtDeadline.text = task.deadline

            when (task.priority) {
                Priority.HIGH ->
                    cardPriority.setCardBackgroundColor(
                        ContextCompat.getColor(itemView.context, R.color.red)
                    )
                Priority.MEDIUM ->
                    cardPriority.setCardBackgroundColor(
                        ContextCompat.getColor(itemView.context, R.color.yellow)
                    )
                Priority.LOW ->
                    cardPriority.setCardBackgroundColor(
                        ContextCompat.getColor(itemView.context, R.color.green)
                    )
            }

            itemView.setOnClickListener {
                onItemClick?.invoke(task)
            }

        }
    }

}
