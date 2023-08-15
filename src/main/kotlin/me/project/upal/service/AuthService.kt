package me.project.upal.service

import me.project.upal.dto.auth.SignInRequest
import me.project.upal.dto.auth.SignUpRequest
import me.project.upal.dto.auth.SignUpResponse
import me.project.upal.dto.auth.TokenDto
import me.project.upal.entity.RefreshToken
import me.project.upal.entity.Role
import me.project.upal.jwt.JwtTokenProvider
import me.project.upal.repository.MemberRepository
import me.project.upal.repository.RefreshTokenRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AuthService(
        private val memberRepository: MemberRepository,
        private val refreshTokenRepository: RefreshTokenRepository,
        private val jwtTokenProvider: JwtTokenProvider,
        private val authenticationManageBuilder: AuthenticationManagerBuilder
) {

    fun authenticate(signInRequest: SignInRequest): ResponseEntity<TokenDto> {
        val authenticationToken: UsernamePasswordAuthenticationToken =
                UsernamePasswordAuthenticationToken(signInRequest.email, signInRequest.password)
        val authentication: Authentication = authenticationManageBuilder.`object`.authenticate(authenticationToken)
        SecurityContextHolder.getContext().authentication = authentication
        val accessToken = jwtTokenProvider.createToken(authentication)
        val refreshToken = jwtTokenProvider.createRefreshToken(authenticationToken)

        refreshTokenRepository.save(RefreshToken(email = signInRequest.email, refreshToken = refreshToken))

        return ResponseEntity<TokenDto>(TokenDto(accessToken, refreshToken), HttpStatus.OK)
    }

    fun signUp(signUpRequest: SignUpRequest): ResponseEntity<SignUpResponse> {
        val member = memberRepository.save(signUpRequest.toEntity())
        member.roles.add(Role("ROLE_USER"))
        return ResponseEntity(SignUpResponse(member), HttpStatus.OK)
    }

    fun refresh(refreshToken: String): ResponseEntity<TokenDto> {
        if (jwtTokenProvider.validateToken(refreshToken)) {
            val authentication = jwtTokenProvider.getAuthentication(refreshToken)
            val tokenDto = TokenDto(
                    accessToken = jwtTokenProvider.createToken(authentication),
                    refreshToken = jwtTokenProvider.createRefreshToken(authentication)
            )

            return ResponseEntity(tokenDto, HttpStatus.OK)
        }
        return ResponseEntity(HttpStatus.BAD_REQUEST)
    }

}