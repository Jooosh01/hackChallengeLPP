package com.onturaa.languagepairingprogram.model

class PartnerRepository {
    data class Partner(val n: String, val netId: String, val year: Int, val targLang: String, val selfScore: Int) {

    }
}