package rjc.bookbackend.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import rjc.bookbackend.config.ChatGptConfig
import rjc.bookbackend.consts.BookConst
import rjc.bookbackend.exception.ChatGptResponseException
import rjc.bookbackend.model.BookResponseBean

/**
 * 質問サービスクラス.
 *
 */
@Service
class QuestionService(
    private val webClientBuilder: WebClient.Builder,
    private val chatGptConfig: ChatGptConfig,
    private val translationService: TranslationService
) {
    @Autowired
    private lateinit var messageSource: MessageSource

    companion object {
        // ログ定義
        private val logger = LoggerFactory.getLogger(QuestionService::class.java)
    }

    /**
     * 書籍に関する質問結果取得処理.
     * ChatGPTに質問を投げ、結果を取得する.
     *
     * @param question 質問
     * @return 質問結果
     */
    fun createBookResponse(question: String?): Mono<BookResponseBean> {
        logger.debug("question：$question")
        chatGptConfig.translateApiKey
        // 質問を英語に翻訳
        return translationService.translateToEnglish(question ?: "").flatMap { translatedQuestion ->
            logger.debug("日本語から英語翻訳結果：{}", translatedQuestion)

            // WebClient.BuilderからWebClientインスタンスを生成
            val webClient = webClientBuilder.build()

            // ChatGPT APIへのリクエストを構築
            webClient.post()
                .uri(BookConst.CHATTING_URL)
                .header("Authorization", "Bearer ${chatGptConfig.chattingApiKey}")
                .bodyValue(
                    mapOf(
                        "model" to "gpt-3.5-turbo",
                        "messages" to listOf(
                            mapOf(
                                "role" to "system",
                                "content" to "PleaseListUpToThreeRelatedBookTitles"
                            ),
                            mapOf(
                                "role" to "user",
                                "content" to "$translatedQuestion, " +
                                        "JapaneseAuthorsOnly,ExcludingTranslatedBooks"
                            )
                        ),
                        "temperature" to 0,
                        "max_tokens" to 120
                    )
                )
                .retrieve()
                .bodyToMono(Map::class.java)
                .flatMap { response ->
                    // 応答エラーの検証
                    if (response["error"] != null) {
                        val errorMessage = messageSource.getMessage(
                            "error.chat.response", null, LocaleContextHolder.getLocale()
                        )
                        Mono.error(ChatGptResponseException(errorMessage))
                    } else {
                        // 回答結果を取得
                        val answer = response["choices"]?.let { choices ->
                            (choices as? List<*>)?.firstOrNull()?.let { firstChoice ->
                                (firstChoice as? Map<*, *>)?.get("message")?.let { message ->
                                    (message as? Map<*, *>)?.get("content") as? String
                                }
                            }
                        } ?: "回答を取得できませんでした"
                        val usage = response["usage"] as? Map<*, *>
                        val tokensSent = usage?.get("prompt_tokens") as? Int ?: 0
                        val tokensReceived = usage?.get("completion_tokens") as? Int ?: 0

                        logger.debug("response: {}", response)
                        logger.info("ChatGPT応答結果: $answer")
                        logger.info("送信トークン数: $tokensSent")
                        logger.info("受信トークン数: $tokensReceived")

                        // ChatGPTからの回答を日本語に翻訳
                        translationService.translateToJapanese(answer).map { translatedAnswer ->
                            BookResponseBean(
                                answer = translatedAnswer.trim()
                            )
                        }
                    }
                }
        }
    }
}
