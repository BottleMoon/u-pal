package me.project.upal.repository

import me.project.upal.entity.InterestTag
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface InterestTagRepository : JpaRepository<InterestTag, UUID> {
}