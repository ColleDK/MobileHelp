package com.colledk.mobilehelp.data.remote.model

import java.util.Date

data class UserRemote(
    val name: String,
    val password: String,
    val gender: GenderRemote,
    val birthday: Date
) {
    enum class GenderRemote {
        MALE,
        FEMALE,
    }
}
