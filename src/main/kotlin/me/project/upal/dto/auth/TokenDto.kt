package me.project.upal.dto.auth

data class TokenDto(
        val accessToken: String,
        val refreshToken: String
) {
}