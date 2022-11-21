package com.colledk.mobilehelp.domain.model

import java.util.Date

data class User(
    val name: String = "",
    val password: String = "",
    val gender: Gender = Gender.FEMALE,
    val birthday: Date = Date(),
) {
    enum class Gender {
        MALE,
        FEMALE,
    }
}
