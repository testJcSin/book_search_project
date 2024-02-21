package rjc.bookbackend.exception

import jakarta.validation.ConstraintViolationException
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {
    companion object {
        // ログ定義
        private val logger = LoggerFactory.getLogger(ExceptionHandler::class.java)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(e: ConstraintViolationException): ResponseEntity<Map<String, String?>> {
        val errors = e.constraintViolations.associate { violation ->
            violation.propertyPath.toString() to violation.message
        }
        return ResponseEntity.badRequest().body(errors)
    }

    @ExceptionHandler(value = [ChatGptResponseException::class, TranslationException::class])
    fun handleApiExceptions(e: RuntimeException): ResponseEntity<Map<String, String?>> {
        logger.error("APIエラー: ${e.message}", e)
        val error = mapOf("error" to e.message)
        return ResponseEntity.status(500).body(error)
    }
}
