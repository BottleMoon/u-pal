package me.project.upal.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import java.util.*


@Entity
class Role(roleName: String) {
    @Id
    val id: UUID = UUID.randomUUID()

    var roleName: String = roleName
        private set

    @ManyToMany(mappedBy = "roles")
    private val members: Set<Member> = mutableSetOf()
}