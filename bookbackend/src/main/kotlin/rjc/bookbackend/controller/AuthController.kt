package rjc.bookbackend.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import rjc.bookbackend.service.AuthService

/**
 * 認証コントローラー.
 *
 */
@RestController
class AuthController(
    private val authService: AuthService
) {

    /**
     * 認証処理
     *
     */
    @GetMapping("/api/auth")
    fun authenticate(@RequestParam("code") authCode: String): ResponseEntity<Map<String, String?>> {
        val accessToken = authService.getAccessTokenUsingAuthCode(authCode).block()
        return ResponseEntity.ok(mapOf("accessToken" to accessToken))
    }
}
