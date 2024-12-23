package io.zethange.secgram.user.entities

import io.zethange.secgram.chat.entities.Chat
import io.zethange.secgram.chat.entities.Message
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

@Entity(name = "secgram_user")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @Column
    var name: String? = null,

    @Column(unique = true)
    private var username: String? = null,

    @Column(unique = true)
    var email: String? = null,

    @Column(unique = true)
    var phoneNumber: String? = null,

    private var password: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var role: Role? = null,

    @ManyToMany(fetch = FetchType.LAZY)
    val chats: Set<Chat> = emptySet(),
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(role?.name))
    }

    override fun getPassword(): String = password!!
    override fun getUsername(): String = username!!
    fun setUsername(username: String) {
        this.username = username
    }
    fun setPassword(password: String) {
        this.password = password
    }
}