package io.zethange.secgram.chat

import io.zethange.secgram.chat.entities.Chat
import io.zethange.secgram.chat.entities.Message
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MessageRepository : JpaRepository<Message, UUID> {
    fun findByChat(chat: Chat, pageable: Pageable): Page<Message>
}