package com.onturaa.languagepairingprogram.model

data class UserRequest (
    val netID: String,
    val name: String,
    val password: String,
    val level: String,
    val languageId: Int,
    val description: String
)