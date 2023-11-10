package me.project.upal.repository

import me.project.upal.entity.Role
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RoleRepository : JpaRepository<Role, String> {
    fun findByRoleName(roleName: String): Optional<Role>;
}