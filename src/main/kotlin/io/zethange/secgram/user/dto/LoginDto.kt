package io.zethange.secgram.user.dto

data class LoginDto(
    var username: String,
    var password: String,
)

data class LoginResponseDto(
    val token: String,
    val user: FullUserDto
)