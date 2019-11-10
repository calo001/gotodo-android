package com.github.calo001.gotodo.ui.task

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.calo001.gotodo.R
import com.github.calo001.gotodo.model.Task
import com.github.calo001.gotodo.ui.login.LoginActivity
import com.github.calo001.gotodo.ui.task.dialog.*
import com.github.calo001.gotodo.util.MySharedPreferences
import com.github.calo001.gotodo.util.color
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_tasks.*

class TasksActivity : AppCompatActivity(), TaskView, TaskActionsListener,
    NewTaskListener,
    UpdateTaskListener,
    DeleteTaskListener {

    private lateinit var presenter: TaskPresenter
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

        presenter = TaskPresenterImpl(this)
        initUI()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            R.id.menu_logout -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun initUI() {
        adapter = TaskAdapter(mutableListOf(), this, this)
        rvTask.adapter = adapter
        rvTask.layoutManager = LinearLayoutManager(this)
        setupToolbar()
        setupSwipe()
        getAllTask()

        adapter.registerAdapterDataObserver(object: RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                checkEmptyAdapter()
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                checkEmptyAdapter()
            }
        })
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbarTask)
        supportActionBar?.title = resources.getString(R.string.my_todo_list)
    }

    private fun setupSwipe() {
        swipeRefresh.setOnRefreshListener {
            getAllTask()
        }
    }

    private fun checkEmptyAdapter() {
        if (adapter.itemCount == 0) {
            showNoTaskMessage(true)
        } else {
            showNoTaskMessage(false)
        }
    }

    fun showNoTaskMessage(visible: Boolean) {
        if (visible) {
            noTaskView.visibility = View.VISIBLE
            rvTask.visibility = View.GONE
        } else {
            noTaskView.visibility = View.GONE
            rvTask.visibility = View.VISIBLE
        }

    }

    fun showTaskDialog(view: View) {
        val ft = supportFragmentManager.beginTransaction()
        val prev = supportFragmentManager.findFragmentByTag(NewTaskDialog.TAG)
        prev?.let { ft.remove(it) }
        ft.addToBackStack(null)

        val newDialog = NewTaskDialog.newInstance()
        newDialog.show(ft, UpdateTaskDialog.TAG)
    }

    override fun showUpdateDialog(task: Task) {
        val ft = supportFragmentManager.beginTransaction()
        val prev = supportFragmentManager.findFragmentByTag(UpdateTaskDialog.TAG)
        prev?.let { ft.remove(it) }
        ft.addToBackStack(null)

        val updateDialog = UpdateTaskDialog.newInstance(task.id, task.title, task.description)
        updateDialog.show(ft, UpdateTaskDialog.TAG)
    }

    override fun showDeleteDialog(task: Task) {
        val ft = supportFragmentManager.beginTransaction()
        val prev = supportFragmentManager.findFragmentByTag(DeleteTaskDialog.TAG)
        prev?.let { ft.remove(it) }
        ft.addToBackStack(null)

        val deleteDialog = DeleteTaskDialog.newInstance(task.id, task.title, task.description)
        deleteDialog.show(ft, DeleteTaskDialog.TAG)
    }

    override fun getAllTask() {
        presenter.getAllTask()
    }

    override fun createTask(title: String, description: String) {
        presenter.createTask(title, description)
    }

    override fun updateTask(id: Int, title: String, description: String) {
        presenter.editTask(id, title, description)
    }

    override fun deleteTask(id: Int) {
        presenter.deleteTask(id)
    }

    override fun onGetAllTaskSuccess(list: List<Task>) {
        adapter.updateAllTask(list.toMutableList())
    }

    override fun onTaskCreatedSuccess(task: Task) {
        adapter.addTask(task)
        rvTask.smoothScrollToPosition(0)
    }

    override fun onTaskUpdatedSuccess(task: Task) {
        adapter.updateTask(task)
    }

    override fun onTaskDeletedSuccess(task: Task) {
        adapter.deleteTask(task)
    }

    override fun onError(code: Int, message: String) {
        when (code) {
            404 -> showNoTaskMessage(true)
            else -> Snackbar.make(taskCoordinator, message, Snackbar.LENGTH_LONG)
                .color(ContextCompat.getColor(this, R.color.colorAccent))
                .show()
        }
    }

    override fun showProgress() {
        swipeRefresh.isRefreshing = true
    }

    override fun hideProgress() {
        swipeRefresh.isRefreshing = false
    }

    fun logout() {
        MySharedPreferences.clearToken()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}

interface TaskActionsListener {
    fun showUpdateDialog(task: Task)
    fun showDeleteDialog(task: Task)
}