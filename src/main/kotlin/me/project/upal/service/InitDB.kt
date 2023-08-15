package me.project.upal.service

import jakarta.annotation.PostConstruct
import me.project.upal.entity.Member
import me.project.upal.entity.Role
import me.project.upal.repository.MemberRepository
import me.project.upal.repository.RoleRepository
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Profile("local")
@Component
@Transactional
class InitDB(private val memberRepository: MemberRepository,
             private val roleRepository: RoleRepository) {
    @PostConstruct
    fun init() {
        var roleUser = Role("ROLE_USER")
        var roleAdmin = Role("ROLE_ADMIN")
        roleUser = roleRepository.save(roleUser)
        roleAdmin = roleRepository.save(roleAdmin)


        for (i: Int in 0..99) {
            val member = Member(email = "email$i",
                    password = "123123",
                    phoneNumber = "phoneNumber$i",
                    nickName = "nickName$i",
                    age = i)
            member.roles.add(roleUser)
            memberRepository.save(member)
        }
    }
}