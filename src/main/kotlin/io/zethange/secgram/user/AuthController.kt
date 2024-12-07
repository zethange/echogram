package io.zethange.secgram.user

import io.swagger.v3.oas.annotations.tags.Tag
import io.zethange.secgram.user.dto.*
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "Auth")
@RequestMapping("/api/v1/auth")
class AuthController(private val userService: UserService) {
    @PostMapping("/login")
    fun login(@RequestBody dto: LoginDto): LoginResponseDto {
        return userService.login(dto)
    }

    @PostMapping("/register")
    fun register(@RequestBody @Valid user: RegisterDto): FullUserDto {
        return userService.register(user)
    }
}