package me.project.upal.repository

import me.project.upal.entity.Tag
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


interface TagRepository : JpaRepository<Tag, UUID> {
    fun findByTag(tag: String): Optional<Tag>
}