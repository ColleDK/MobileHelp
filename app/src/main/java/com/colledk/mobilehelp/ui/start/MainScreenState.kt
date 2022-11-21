package com.colledk.mobilehelp.ui.start

import com.colledk.mobilehelp.domain.model.User
import java.util.Date

data class MainScreenState(
    val currentUser: User = User(
        name = "",
        password = "",
        gender = User.Gender.MALE,
        birthday = Date()
    )
)
