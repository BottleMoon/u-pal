package me.project.upal.service

import me.project.upal.dto.auth.SignUpRequest
import me.project.upal.dto.member.MemberResponse
import me.project.upal.entity.Member
import me.project.upal.repository.CountryRepository
import me.project.upal.repository.MemberRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberService(private val memberRepository: MemberRepository,
                    private val countryRepository: CountryRepository) {
    fun recommend(pageable: Pageable): Page<MemberResponse> {
        return memberRepository.findAll(pageable).map(::MemberResponse)
    }

    fun dtoToEntity(dto: SignUpRequest): Member {
        return Member(email = dto.email,
                password = BCryptPasswordEncoder().encode(dto.password),
                age = dto.age,
                phoneNumber = dto.phoneNumber,
                nickName = dto.nickName,
                country = countryRepository.findById(dto.country).orElseThrow()
        )
    }
}