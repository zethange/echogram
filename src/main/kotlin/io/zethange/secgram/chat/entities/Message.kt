package io.zethange.secgram.chat.entities

import io.zethange.secgram.user.entities.User
import jakarta.persistence.*
import java.util.*

@Entity(name = "message")
class Message(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column
    val message: String,

    @ManyToOne(fetch = FetchType.LAZY)
    val author: User,

    @ManyToOne(fetch = FetchType.LAZY)
    val chat: Chat,
)