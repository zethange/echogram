package io.zethange.secgram.user

import io.zethange.secgram.user.entities.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, UUID> {
    fun findByUsername(username: String): User?

    @Query("select u from secgram_user u where u.username ilike %:username%")
    fun searchByUsername(@Param("username") username: String?, pageable: Pageable): Page<User>
}