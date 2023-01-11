package com.colledk.mobilehelp.data.remote.model

import java.util.Date

data class UserRemote(
    val name: String = "",
    val password: String = "",
    val gender: GenderRemote = GenderRemote.MALE,
    val birthday: Date = Date()
) {
    enum class GenderRemote(name: String) {
        MALE("male"),
        FEMALE("female"),
    }
}
