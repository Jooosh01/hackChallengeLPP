package com.onturaa.languagepairingprogram.model

import java.util.Dictionary

class PartnerRepository {
    enum class Languages{
        Japanese,
        Patwa,
        English,
        Twi
    }
    data class Partner(val n: String, val netId: String, val year: Int, val languages: Dictionary<Languages, Int>) {

    }
}