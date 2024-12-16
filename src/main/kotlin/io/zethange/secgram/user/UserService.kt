package io.zethange.secgram.user

import io.zethange.secgram.user.dto.*
import io.zethange.secgram.user.entities.Role
import io.zethange.secgram.user.entities.User
import io.zethange.secgram.utils.CustomException
import io.zethange.secgram.utils.JwtService
import jakarta.transaction.Transactional
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(private val userRepository: UserRepository, private val jwtService: JwtService, private val passwordEncoder: PasswordEncoder) : UserDetailsService {
    fun getUsers(pageable: Pageable, search: String): List<UserDto> {
        return userRepository.searchByUsername(search, pageable).map { it.toDto() }.toList()
    }

    @Transactional
    fun register(dto: RegisterDto): FullUserDto {
        val user = dto.toUser()
        user.password = passwordEncoder.encode(dto.password)

        if (userRepository.count() == 0L) {
            user.role = Role.ADMIN
        } else {
            user.role = Role.USER
        }

        val savedUser = userRepository.save(user)

        return savedUser.toFullDto()
    }

    fun getUserById(id: UUID): UserDto {
        return userRepository.findById(id).map { it.toDto() }.orElseThrow {
            CustomException(HttpStatus.NOT_FOUND, "User with id $id not found")
        }
    }

    fun login(dto: LoginDto): LoginResponseDto {
        val user = userRepository.findByUsername(dto.username) ?: throw CustomException(HttpStatus.UNAUTHORIZED, "Unauthorized")

        if (!passwordEncoder.matches(dto.password, user.password)) {
            throw CustomException(HttpStatus.UNAUTHORIZED, "Invalid password")
        }

        val token = jwtService.generateToken(user)

        return LoginResponseDto(
            token = token,
            user = user.toFullDto()
        )
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        val user = userRepository.findByUsername(username.orEmpty()) ?: throw UsernameNotFoundException("User not found")

        return user
    }

    fun getCurrentUser(): User {
        val username = SecurityContextHolder.getContext().authentication.name
        return userRepository.findByUsername(username) ?: throw CustomException(HttpStatus.UNAUTHORIZED, "Unauthorized")
    }

    @Transactional
    fun putUser(dto: RegisterDto): FullUserDto {
        val user = getCurrentUser()

        user.name = dto.name
        user.username = dto.username
        user.password = passwordEncoder.encode(dto.password)
        user.phoneNumber = dto.phoneNumber
        user.email = dto.email

        userRepository.save(user)

        return user.toFullDto()
    }
}