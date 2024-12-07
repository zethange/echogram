package io.zethange.secgram.user

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import io.zethange.secgram.user.dto.FullUserDto
import io.zethange.secgram.user.dto.RegisterDto
import io.zethange.secgram.user.dto.UserDto
import io.zethange.secgram.user.dto.toFullDto
import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users")
class UserController(private val userService: UserService) {
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    fun getUsers(): List<UserDto> {
        return userService.getUsers()
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: UUID): UserDto {
        return userService.getUserById(id)
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get current authenticated user")
    fun getCurrentUser(): FullUserDto {
        return userService.getCurrentUser().toFullDto()
    }

    @PutMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Update current authenticated user")
    fun putUser(@RequestBody @Valid dto: RegisterDto): FullUserDto {
        return userService.putUser(dto)
    }
}