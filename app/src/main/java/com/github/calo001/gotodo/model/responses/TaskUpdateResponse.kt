package com.github.calo001.gotodo.model.responses

import com.github.calo001.gotodo.model.Task

data class TaskUpdateResponse (
    val message: String,
    val task: Task
)