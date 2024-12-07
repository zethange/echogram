package io.zethange.secgram.user.dto

import io.zethange.secgram.user.entities.Role
import io.zethange.secgram.user.entities.User
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RegisterDto(
    @field:NotBlank
    @field:Size(min = 1, max = 255)
    var name: String,

    @field:NotBlank
    @field:Size(max = 100)
    var username: String,

    @field:Email
    @field:Size(max = 100)
    var email: String,

    @field:NotBlank
    @field:Size(max = 100)
    var phoneNumber: String,

    @field:Size(min = 8, max = 32)
    var password: String,
) {
    fun toUser(): User {
        return User(
            name = name,
            username = username,
            email = email,
            phoneNumber = phoneNumber,
            password = password,
            role = Role.USER
        )
    }
}
