package com.github.calo001.gotodo.ui.task

import com.github.calo001.gotodo.model.Task

class TaskPresenterImpl(private val view: TaskView): TaskPresenter {
    private val interactor: TaskInteractor = TaskInteractorImpl(this)

    override fun getAllTask() {
        view.showProgress()
        interactor.getAllTask()
    }

    override fun onGetAllTaskSuccess(list: List<Task>) {
        view.hideProgress()
        view.onGetAllTaskSuccess(list)
    }

    override fun onError(code: Int, message: String) {
        view.hideProgress()
        view.onError(code, message)
    }

    override fun createTask(title: String, description: String) {
        interactor.createTask(title, description)
    }

    override fun onTaskCreatedSuccess(task: Task) {
        view.onTaskCreatedSuccess(task)
    }

    override fun editTask(id: Int, title: String, description: String) {
        interactor.updateTask(id, title, description)
    }

    override fun onTaskUpdatedSuccess(task: Task) {
        view.onTaskUpdatedSuccess(task)
    }

    override fun deleteTask(id: Int) {
        interactor.deleteTask(id)
    }

    override fun onTaskDeletedSuccess(task: Task) {
        view.onTaskDeletedSuccess(task)
    }
}