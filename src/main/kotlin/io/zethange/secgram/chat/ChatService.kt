package io.zethange.secgram.chat

import io.zethange.secgram.chat.dto.ChatDto
import io.zethange.secgram.chat.dto.CreateChatDto
import io.zethange.secgram.chat.dto.MessageDto
import io.zethange.secgram.chat.dto.toDto
import io.zethange.secgram.chat.entities.Chat
import io.zethange.secgram.chat.entities.ChatType
import io.zethange.secgram.user.UserService
import io.zethange.secgram.user.entities.User
import io.zethange.secgram.utils.CustomException
import io.zethange.secgram.utils.PageDto
import io.zethange.secgram.utils.toDto
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.*

@Service
class ChatService(private val chatRepository: ChatRepository, private val messageRepository: MessageRepository, private val userService: UserService) {
    fun getMyChats(pageable: Pageable): PageDto<ChatDto> {
        val user = userService.getCurrentUser()

        val result = chatRepository.findByUserId(user.id!!, pageable)

        return result.map {
            val dto = it.toDto()
            if (dto.type == ChatType.PRIVATE) {
                val title = dto.participants.first { u -> u.id != user.id }.name
                dto.title = title
            }
            dto
        }.toDto()
    }

    fun getMessagesByChatId(chatId: UUID, pageable: Pageable): PageDto<MessageDto> {
        val user = userService.getCurrentUser()

        val chat = chatRepository.findById(chatId).orElseThrow {
            throw CustomException(
                httpStatus = HttpStatus.NOT_FOUND,
                message = "No chat with ID $chatId",
            )
        }

        if (!chat.participants.contains(user)) {
            throw CustomException(
                httpStatus = HttpStatus.FORBIDDEN,
                message = "You don't have access to this chat"
            )
        }

        val messagePage = messageRepository.findByChat(chat, pageable)

        return messagePage.map { it.toDto() }.toDto()
    }

    fun createChat(dto: CreateChatDto): ChatDto {
        val chat = Chat(
            type = dto.type,
            title = dto.title,
            messages = emptySet(),
            participants = dto.participants.map { User(
                id = it,
            ) }.toSet()
        )

        return chatRepository.save(chat).toDto()
    }
}