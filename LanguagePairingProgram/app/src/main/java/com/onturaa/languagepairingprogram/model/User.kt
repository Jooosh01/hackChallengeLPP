package com.onturaa.languagepairingprogram.model

data class User (
    val netID: String,
    val name: String,
    val password: String,
    val level: String,
    val language: String,
    val bio: String
)