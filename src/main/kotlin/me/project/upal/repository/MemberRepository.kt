package me.project.upal.repository

import me.project.upal.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberRepository : JpaRepository<Member, UUID> {
    fun findByEmail(email: String): Optional<Member>
}