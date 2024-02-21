package rjc.bookbackend.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import rjc.bookbackend.config.OAuthConfig
import rjc.bookbackend.consts.BookConst
import rjc.bookbackend.exception.AccessTokenRetrievalException
import rjc.bookbackend.model.AccessTokenBean
import rjc.bookbackend.util.EncryptionUtil

/**
 * 認証サービスクラス.
 */
@Service
class AuthService(private val webClient: WebClient.Builder, private val oauthConfig: OAuthConfig) {
    @Autowired
    private lateinit var messageSource: MessageSource

    /**
     * 認証処理.
     * @param authCode 認証コード
     * @return String accessToken
     */
    fun getAccessTokenUsingAuthCode(authCode: String): Mono<String> {
        return webClient.build().post()
            .uri(BookConst.OAUTH2_URL)
            .bodyValue(
                mapOf(
                    "code" to authCode,
                    "client_id" to oauthConfig.clientId,
                    "client_secret" to oauthConfig.clientSecret,
                    "redirect_uri" to oauthConfig.redirectUri,
                    "grant_type" to "authorization_code"
                )
            )
            .retrieve()
            .bodyToMono(AccessTokenBean::class.java)
            .map { response ->
                EncryptionUtil.encrypt(response.accessToken)
            }
            .onErrorMap { error ->
                val errorMessage = messageSource.getMessage(
                    "error.accession.retrieval.failed", null, LocaleContextHolder.getLocale()
                )
                throw AccessTokenRetrievalException(errorMessage, error)
            }
    }
}
