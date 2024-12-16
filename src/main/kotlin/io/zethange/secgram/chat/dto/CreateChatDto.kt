package io.zethange.secgram.chat.dto

import io.zethange.secgram.chat.entities.ChatType
import java.util.*

data class CreateChatDto(
    val type: ChatType,
    val title: String,
    val participants: Set<UUID>
)