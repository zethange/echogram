package io.zethange.secgram.chat

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import io.zethange.secgram.chat.dto.ChatDto
import io.zethange.secgram.chat.dto.MessageDto
import io.zethange.secgram.utils.PageDto
import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@Tag(name = "Chats")
@RestController
@RequestMapping("/api/v1/chats")
class ChatController(private val chatService: ChatService) {
    @GetMapping
    @Operation(summary = "Get my chats")
    @PreAuthorize("isAuthenticated()")
    fun getMyChats(pageable: Pageable): PageDto<ChatDto> {
        return chatService.getMyChats(pageable)
    }

    @GetMapping("/{chatId}/messages")
    @PreAuthorize("isAuthenticated()")
    fun getMessagesByChatId(@PathVariable chatId: UUID, pageable: Pageable): PageDto<MessageDto> {
        return chatService.getMessagesByChatId(chatId, pageable)
    }
}