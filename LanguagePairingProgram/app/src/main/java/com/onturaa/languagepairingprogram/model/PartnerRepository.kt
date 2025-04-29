package com.onturaa.languagepairingprogram.model

class PartnerRepository {
    enum class Languages{
        Japanese,
        Patwa,
        English,
        Twi
    }
    data class Partner(val n: String, val netId: String, val year: Int, val languages: Map<Languages, Int>) {

    }
}