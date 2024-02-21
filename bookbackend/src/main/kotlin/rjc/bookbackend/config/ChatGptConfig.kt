package rjc.bookbackend.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class ChatGptConfig {

    @Value("\${CHATTING_API_KEY}")
    lateinit var chattingApiKey: String

    @Value("\${TRANSLATE_API_KEY}")
    lateinit var translateApiKey: String
}
