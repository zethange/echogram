package io.zethange.secgram.chat.entities

import io.zethange.secgram.user.entities.User
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant
import java.util.*

@Entity(name = "chat")
class Chat(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Enumerated(EnumType.STRING)
    val type: ChatType,

    val title: String,

    @ManyToMany(fetch = FetchType.EAGER)
    val participants: Set<User> = emptySet(),

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable
    val messages: Set<Message> = emptySet(),

    @CreationTimestamp
    val createdAt: Instant? = null,

    @UpdateTimestamp
    val updatedAt: Instant? = null,
);