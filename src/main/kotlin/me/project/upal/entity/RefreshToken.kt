package me.project.upal.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash(value = "refreshToken", timeToLive = 60 * 60 * 24 * 7 * 2)
class RefreshToken(
        refreshToken: String,
        email: String
) {
    @Id
    var email: String = email
        private set

    var refreshToken: String = refreshToken
        private set

}