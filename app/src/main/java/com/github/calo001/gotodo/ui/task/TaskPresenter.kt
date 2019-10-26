package com.github.calo001.gotodo.ui.task

import com.github.calo001.gotodo.model.Task

interface TaskPresenter {
    fun getAllTask()
    fun createTask(title: String, description: String)
    fun editTask(id: Int, title: String, description: String)
    fun deleteTask(id: Int)
    fun onGetAllTaskSuccess(list: List<Task>)
    fun onTaskCreatedSuccess(task: Task)
    fun onTaskUpdatedSuccess(task: Task)
    fun onTaskDeletedSuccess(task: Task)
    fun onError(code: Int, message: String)
}