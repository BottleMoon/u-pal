package me.project.upal.service

import me.project.upal.entity.Member
import me.project.upal.repository.MemberRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class UserDetailsServiceImpl(private val memberRepository: MemberRepository) : UserDetailsService {

    override fun loadUserByUsername(email: String?): UserDetails {
        val member = memberRepository.findByEmail(email!!).orElseThrow { Exception("User not found") }
        return createUser(email, member)
    }

    fun createUser(email: String?, member: Member): User {
        val grantedAuthorities: List<GrantedAuthority> =
                member.roles.map { SimpleGrantedAuthority(it.roleName) }.toList()
        return User(email, member.password, grantedAuthorities)
    }

}