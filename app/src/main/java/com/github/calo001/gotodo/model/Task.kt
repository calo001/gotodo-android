package com.github.calo001.gotodo.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Task(
    var id: Int,
    var title: String,
    var description: String,
    @SerializedName("created_at")
    var CreatedAt: Date) {

    fun clear() {
        id = 0
        title = ""
        description = ""
        CreatedAt = Date()
    }
}