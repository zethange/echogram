package io.zethange.secgram.user

import io.zethange.secgram.user.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, UUID> {
    fun findByUsername(username: String): User?
}