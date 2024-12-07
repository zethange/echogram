package io.zethange.secgram.chat

import io.zethange.secgram.chat.entities.Chat
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ChatRepository : JpaRepository<Chat, UUID> {
    @Query("""
        select c from chat c
        join c.participants p
        where p.id = :user_id
    """)
    fun findByUserId(@Param("user_id") userId: UUID, pageable: Pageable): Page<Chat>
}