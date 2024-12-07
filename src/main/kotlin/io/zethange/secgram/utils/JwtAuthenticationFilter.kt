package io.zethange.secgram.utils

import io.zethange.secgram.user.UserService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Lazy
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(private val jwtService: JwtService, private val userService: UserService) : OncePerRequestFilter() {
    private val BEARER_PREFIX = "Bearer "
    private val AUTHORIZATION_HEADER = "Authorization"

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader(AUTHORIZATION_HEADER) ?: ""

        if (authHeader.isEmpty() || !authHeader.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response)
            return
        }

        val jwt = authHeader.substring(BEARER_PREFIX.length)
        try {
            val username = jwtService.extractUsername(jwt)

            if (username.isNotEmpty() && (SecurityContextHolder.getContext().authentication == null || SecurityContextHolder.getContext().authentication is AnonymousAuthenticationToken)) {
                val userDetails = userService.loadUserByUsername(username)
                if (jwtService.isTokenValid(jwt)) {
                    val context = SecurityContextHolder.createEmptyContext()
                    val authToken = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)

                    authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                    context.authentication = authToken
                    SecurityContextHolder.setContext(context)
                }
            }
        } catch (e: Exception) {
            println(e.message)
        }

        filterChain.doFilter(request, response)
    }
}