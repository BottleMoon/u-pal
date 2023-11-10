package me.project.upal.controller

import me.project.upal.dto.member.MemberResponse
import me.project.upal.service.MemberService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/member")
class MemberController(private val memberService: MemberService) {

    @GetMapping("/list")
    fun recommend(pageable: Pageable): Page<MemberResponse> {
        return memberService.recommend(pageable)
    }

    @GetMapping("/confirm")
    fun confirmEmail(email: String) {
        //TODO: 이메일 확인
    }
}