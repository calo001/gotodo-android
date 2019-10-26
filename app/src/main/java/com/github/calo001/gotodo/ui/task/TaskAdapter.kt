package com.github.calo001.gotodo.ui.task

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.calo001.gotodo.R
import com.github.calo001.gotodo.model.Task
import kotlinx.android.synthetic.main.item_task.view.*
import java.text.SimpleDateFormat

class TaskAdapter(private var items: MutableList<Task>,
                  private val context: Context,
                  private val listener: TaskActionsListener): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    var dateFormat = SimpleDateFormat("E dd MMMM yyyy")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder =
        TaskViewHolder(LayoutInflater.from(context).inflate(R.layout.item_task, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val date = dateFormat.format(items[position].CreatedAt)

        holder.tvTitle.text = items[position].title
        holder.tvDescription.text = items[position].description
        holder.tvCreatedAt.text = date

        holder.cvItemTask.setOnClickListener {
            listener.showUpdateDialog(items[position])
        }

        holder.btnDelete.setOnClickListener {
            listener.showDeleteDialog(items[position])
        }
    }

    fun updateAllTask(list: MutableList<Task>) {
        items = list
        notifyDataSetChanged()
    }

    fun updateTask(task: Task) {
        val item = items.single { t -> t.id == task.id }
        val index = items.indexOf(item)

        items[index] = task
        notifyItemChanged(index)
    }

    fun deleteTask(task: Task) {
        val item = items.single { t -> t.id == task.id }
        val index = items.indexOf(item)

        items.removeAt(index)
        notifyItemRemoved(index)
        notifyItemRangeChanged(index, itemCount)
    }

    fun addTask(task: Task) {
        items.add(0, task)

        if (itemCount <= 1) {
            notifyDataSetChanged()
        } else {
            notifyItemInserted(0)
            notifyItemRangeChanged(0, itemCount)
        }
    }

    class TaskViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvTitle = view.tvTitle
        val tvDescription = view.tvDescription
        val btnDelete = view.btnOk
        val cvItemTask = view.cvItemTask
        val tvCreatedAt = view.tvCreatedAt
    }
}