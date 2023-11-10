package me.project.upal.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash(value = "emailAuthCode", timeToLive = 60 * 30)
class EmailAuthCode(
        code: String,
        email: String
) {
    @Id
    var email: String = email
        private set

    var code: String = code
        private set
}