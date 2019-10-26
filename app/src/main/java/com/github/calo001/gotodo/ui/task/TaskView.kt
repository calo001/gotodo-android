package com.github.calo001.gotodo.ui.task

import com.github.calo001.gotodo.model.Task

interface TaskView {
    fun getAllTask()
    fun onGetAllTaskSuccess(list: List<Task>)
    fun onError(code: Int, message: String)
    fun showProgress()
    fun hideProgress()
    fun onTaskCreatedSuccess(task: Task)
    fun onTaskUpdatedSuccess(task: Task)
    fun onTaskDeletedSuccess(task: Task)
}