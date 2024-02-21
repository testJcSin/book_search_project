package rjc.bookbackend.config

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import rjc.bookbackend.util.EncryptionUtil

@Configuration
class OAuthConfig {

    @Value("\${GOOGLE_CLIENT_ID}")
    lateinit var clientId: String

    @Value("\${GOOGLE_CLIENT_SECRET}")
    lateinit var clientSecret: String

    @Value("\${GOOGLE_REDIRECT_URI}")
    lateinit var redirectUri: String

    @Value("\${SECRET_KEY}")
    lateinit var secretKey: String

    @PostConstruct
    fun init() {
        EncryptionUtil.setSecretKey(secretKey)
    }
}
