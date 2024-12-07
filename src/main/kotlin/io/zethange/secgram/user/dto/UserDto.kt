package io.zethange.secgram.user.dto

import io.zethange.secgram.user.entities.Role
import io.zethange.secgram.user.entities.User
import java.util.UUID

data class UserDto(
    val id: UUID,
    val name: String,
    val username: String,
    val role: Role,
)

fun User.toDto(): UserDto {
    return UserDto(
        id = id!!,
        name = name,
        username = username,
        role = role
    )
}
