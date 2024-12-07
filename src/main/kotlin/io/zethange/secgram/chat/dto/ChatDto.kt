package io.zethange.secgram.chat.dto

import io.zethange.secgram.chat.entities.Chat
import io.zethange.secgram.chat.entities.ChatType
import io.zethange.secgram.user.dto.UserDto
import io.zethange.secgram.user.dto.toDto
import java.time.Instant
import java.util.UUID

data class ChatDto(
    val id: UUID,
    val type: ChatType,
    var title: String,
    val participants: Set<UserDto>,
    val createdAt: Instant,
    val updatedAt: Instant,
)

fun Chat.toDto(): ChatDto = ChatDto(
    id = id!!,
    title = title,
    type = type,
    participants = participants.map { it.toDto() }.toSet(),
    createdAt = createdAt!!,
    updatedAt = updatedAt!!,
)