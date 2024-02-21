package rjc.bookbackend.model

import com.fasterxml.jackson.annotation.JsonProperty

data class AccessTokenBean(
    @JsonProperty("access_token") val accessToken: String,
    @JsonProperty("expires_in") val expiresIn: Long,
    @JsonProperty("token_type") val tokenType: String
)
