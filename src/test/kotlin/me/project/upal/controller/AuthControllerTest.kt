package me.project.upal.controller

import io.swagger.v3.core.util.Json
import me.project.upal.dto.auth.SignInRequest
import me.project.upal.dto.auth.SignUpRequest
import me.project.upal.dto.auth.TokenDto
import me.project.upal.jwt.JwtTokenProvider
import me.project.upal.repository.MemberRepository
import me.project.upal.service.MemberService
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class AuthControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var memberService: MemberService

    @Autowired
    private lateinit var memberRepository: MemberRepository

    @Autowired
    private lateinit var jwtTokenProvider: JwtTokenProvider

    @Test
    fun signIn() {
        //given
        val signUpRequest = SignUpRequest(
                email = "email",
                password = "password",
                phoneNumber = "phoneNumber",
                nickName = "nickName",
                age = 1,
                country = "한국",
                interestTags = listOf("친구"),
                interestCountries = listOf("KR")
        )
        val member = memberService.dtoToEntity(signUpRequest)

        memberRepository.save(member)

        val email = "email"
        val password = "password"
        val signInRequest = SignInRequest(email, password)

        //when
        val res = mockMvc.perform(MockMvcRequestBuilders
                .post("/auth/token")
                .content(Json.mapper().writeValueAsString(signInRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

                //then
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

        val tokenDto = Json.mapper().readValue(res.response.contentAsString, TokenDto::class.java)
        val token = tokenDto.accessToken
        val refreshToken = tokenDto.refreshToken
        assertTrue(jwtTokenProvider.validateToken(token))
        assertTrue(jwtTokenProvider.validateToken(refreshToken))
    }

}