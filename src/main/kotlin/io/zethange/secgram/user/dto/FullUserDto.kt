package io.zethange.secgram.user.dto

import io.zethange.secgram.user.entities.Role
import io.zethange.secgram.user.entities.User
import java.util.*

data class FullUserDto(
    val id: UUID,
    val name: String,
    val username: String,
    val phoneNumber: String,
    val email: String,
    val role: Role,
)

fun User.toFullDto(): FullUserDto {
    return FullUserDto(
        id = id!!,
        name = name!!,
        username = username,
        email = email!!,
        phoneNumber = phoneNumber!!,
        role = role!!
    )
}