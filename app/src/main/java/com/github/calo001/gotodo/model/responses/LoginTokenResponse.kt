package com.github.calo001.gotodo.model.responses

data class LoginTokenResponse(
    val expire: String,
    val token: String
)