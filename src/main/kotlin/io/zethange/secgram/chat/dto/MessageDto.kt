package io.zethange.secgram.chat.dto

import io.zethange.secgram.chat.entities.Message
import io.zethange.secgram.user.dto.UserDto
import io.zethange.secgram.user.dto.toDto
import java.util.*

data class MessageDto(
    val id: UUID,
    val message: String,
    val author: UserDto,
)

fun Message.toDto(): MessageDto = MessageDto(
    id = id!!,
    message = message,
    author = author.toDto(),
)