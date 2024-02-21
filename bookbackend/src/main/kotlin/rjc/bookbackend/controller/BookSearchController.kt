package rjc.bookbackend.controller

import jakarta.validation.constraints.NotEmpty
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import rjc.bookbackend.model.BookResponseBean
import rjc.bookbackend.service.QuestionService

/**
 * 書籍検索コントローラー.
 *
 */
@RestController
@Validated
class BookSearchController(private val questionService: QuestionService) {

    /**
     * 書籍検索処理.
     */
    @GetMapping("/bookSearch")
    fun bookSearch(
        @RequestParam @NotEmpty(message = "{bookSearch.question.notBlank}") question: String
    ): Mono<BookResponseBean> {
        return questionService.createBookResponse(question)
    }
}
