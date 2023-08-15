package me.project.upal.dto.auth

import me.project.upal.entity.Member
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

data class SignUpRequest(
        val email: String,
        val password: String,
        val phoneNumber: String,
        val nickName: String,
        val age: Int
) {
    fun toEntity(): Member {
        return Member(
                email,
                BCryptPasswordEncoder().encode(password),
                phoneNumber,
                nickName,
                age
        )
    }
}