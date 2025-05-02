package com.onturaa.languagepairingprogram.model

class PartnerRepository {
    data class Partner(
        val id: Int,
        val netId: String,
        val name: String,
        val level: String,
        val match_status: Boolean,
        val description: String?,
        val language: Language
    )

    data class Language(
        val id: Int,
        val name: String,
        val flag_url: String
    )
}