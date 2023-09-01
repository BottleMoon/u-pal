package me.project.upal.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import me.project.upal.dto.auth.SignUpRequest
import me.project.upal.jwt.JwtTokenProvider
import me.project.upal.repository.MemberRepository
import me.project.upal.repository.RefreshTokenRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.transaction.annotation.Transactional
import java.util.*

@ExtendWith(MockKExtension::class)
@Transactional
class AuthServiceTest {
    @MockK
    private lateinit var memberService: MemberService

    @MockK
    private lateinit var memberRepository: MemberRepository

    @MockK
    private lateinit var refreshTokenRepository: RefreshTokenRepository

    @MockK
    private lateinit var jwtTokenProvider: JwtTokenProvider

    @MockK
    private lateinit var authenticationManageBuilder: AuthenticationManagerBuilder

    @InjectMockKs
    private lateinit var authService: AuthService

    @Test
    fun signUp() {
        //given
        val signUpRequest = SignUpRequest(
                email = "email",
                password = "password",
                phoneNumber = "phoneNumber",
                nickName = "nickName",
                age = 1,
                country = "한국"
        )
        val member = memberService.dtoToEntity(signUpRequest)
        every { memberRepository.save(any()) } returns member
        every { memberRepository.findById(any()) } returns Optional.of(member)
        //when
        authService.signUp(signUpRequest)

        //then
        val findMember = memberRepository.findById(member.id).get()
        assert(findMember.email == member.email)
        assert(findMember.password == member.password)
        assert(findMember.phoneNumber == member.phoneNumber)
        assert(findMember.nickName == member.nickName)
        assert(findMember.age == member.age)

    }
}