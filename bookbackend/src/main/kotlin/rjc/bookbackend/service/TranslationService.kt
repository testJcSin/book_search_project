package rjc.bookbackend.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import rjc.bookbackend.config.ChatGptConfig
import rjc.bookbackend.exception.TranslationException

@Service
class TranslationService(
    private val webClientBuilder: WebClient.Builder,
    private val chatGptConfig: ChatGptConfig
) {
    @Autowired
    private lateinit var messageSource: MessageSource

    /**
     * 翻訳処理.
     *
     * @param text 翻訳対象文字列
     * @param sourceLang 翻訳元言語
     * @param targetLang 翻訳先言語
     * @return Mono<String> 翻訳結果を含むMono
     */
    fun translate(text: String, sourceLang: String, targetLang: String): Mono<String> {
        return webClientBuilder.build().post()
            .uri { uriBuilder ->
                uriBuilder
                    .scheme("https")
                    .host("translation.googleapis.com")
                    .path("/language/translate/v2")
                    .queryParam("key", chatGptConfig.translateApiKey)
                    .build()
            }
            .bodyValue(
                mapOf(
                    "q" to text,
                    "source" to sourceLang,
                    "target" to targetLang,
                    "format" to "text"
                )
            )
            .retrieve()
            .bodyToMono(Map::class.java)
            .flatMap { response ->
                val data = response["data"] as? Map<*, *>
                val translations = data?.get("translations")
                if (translations is List<*>) {
                    val firstTranslation = translations.firstOrNull() as? Map<*, *>
                    if (firstTranslation != null) {
                        Mono.just(firstTranslation["translatedText"] as? String ?: "")
                    } else {
                        createErrorMono()
                    }
                } else {
                    createErrorMono()
                }
            }
    }

    /**
     * 英語から日本語への翻訳を行う.
     *
     * @param text 翻訳対象文字列
     * @return Mono<String> 翻訳結果を含むMono
     */
    fun translateToJapanese(text: String): Mono<String> {
        return translate(text, "en", "ja")
    }

    /**
     * 日本語から英語への翻訳を行う.
     *
     * @param text 翻訳対象文字列
     * @return Mono<String> 翻訳結果を含むMono
     */
    fun translateToEnglish(text: String): Mono<String> {
        return translate(text, "ja", "en")
    }

    /**
     * エラーメッセージを取得し、TranslationExceptionを生成する.
     *
     * @return Mono<String> エラーを含むMono
     */
    private fun createErrorMono(): Mono<String> {
        val errorMessage = messageSource.getMessage(
            "error.translation.failed", null, LocaleContextHolder.getLocale()
        )
        return Mono.error(TranslationException(errorMessage))
    }

}
