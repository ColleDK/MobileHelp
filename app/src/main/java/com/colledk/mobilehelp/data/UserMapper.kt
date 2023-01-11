package com.colledk.mobilehelp.data

import com.colledk.mobilehelp.data.local.model.UserLocal
import com.colledk.mobilehelp.data.remote.model.UserRemote
import com.colledk.mobilehelp.domain.model.User
import java.util.Date

fun User.mapToLocal(): UserLocal {
    val currentGender: UserLocal.GenderLocal = when (gender) {
        User.Gender.FEMALE -> {
            UserLocal.GenderLocal.FEMALE
        }
        User.Gender.MALE -> {
            UserLocal.GenderLocal.MALE
        }
    }

    return UserLocal(
        name = name,
        password = password,
        birthday = birthday,
        gender = currentGender,
    )
}

fun User.mapToRemote(): UserRemote {
    val currentGender: UserRemote.GenderRemote = when (gender) {
        User.Gender.FEMALE -> {
            UserRemote.GenderRemote.FEMALE
        }
        User.Gender.MALE -> {
            UserRemote.GenderRemote.MALE
        }
    }

    return UserRemote(
        name = name,
        password = password,
        birthday = birthday,
        gender = currentGender
    )
}

fun UserRemote.mapToDomain(): User {
    val currentGender: User.Gender = when (gender) {
        UserRemote.GenderRemote.FEMALE -> {
            User.Gender.FEMALE
        }
        UserRemote.GenderRemote.MALE -> {
            User.Gender.MALE
        }
    }

    return User(
        name = name,
        password = password,
        birthday = birthday,
        gender = currentGender
    )
}

fun UserLocal.mapToDomain(): User {
    val currentGender: User.Gender = when (gender) {
        UserLocal.GenderLocal.FEMALE -> {
            User.Gender.FEMALE
        }
        UserLocal.GenderLocal.MALE -> {
            User.Gender.MALE
        }
    }

    return User(
        name = name,
        password = password,
        birthday = birthday,
        gender = currentGender
    )
}

fun UserRemote.toHashMap(): HashMap<String, Any>{
    return hashMapOf(
        "name" to this.name,
        "password" to this.password,
        "birthday" to this.birthday.time,
        "gender" to this.gender.name
    )
}

fun Map<String, Any>?.toUserRemote(): UserRemote {
    return this?.let {
        UserRemote(
            name = it["name"].toString(),
            password = it["password"].toString(),
            gender = UserRemote.GenderRemote.valueOf(it["gender"].toString()),
            birthday = Date(it["birthday"].toString().toLong())
        )
    } ?: UserRemote()
}