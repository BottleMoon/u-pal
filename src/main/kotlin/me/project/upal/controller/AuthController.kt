package me.project.upal.controller

import me.project.upal.dto.auth.*
import me.project.upal.repository.RefreshTokenRepository
import me.project.upal.service.AuthService
import me.project.upal.service.EmailService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(private val authService: AuthService, private val emailService: EmailService, private val refresh: RefreshTokenRepository) {

    @PostMapping("/token")
    fun authenticate(@RequestBody signInRequest: SignInRequest): ResponseEntity<TokenDto> {
        return authService.authenticate(signInRequest)
    }

    @PostMapping("/member")
    fun signUp(@RequestBody signUpRequest: SignUpRequest): ResponseEntity<SignUpResponse> {
        return authService.signUp(signUpRequest)
    }

    @PostMapping("/refresh")
    fun refresh(@RequestBody refreshToken: String): ResponseEntity<TokenDto> {
        return authService.refresh(refreshToken)
    }

    @PostMapping("/send-email")
    fun sendEmail(@RequestBody dto: EmailRequest): ResponseEntity<Map<String, String>> {
        return emailService.sendMail(dto.email)
    }

    @PostMapping("/authenticate-email")
    fun authenticateEmail(@RequestBody dto: EmailRequest): ResponseEntity<Void> {
        return emailService.authenticateEmail(dto.email, dto.code!!)
    }

}