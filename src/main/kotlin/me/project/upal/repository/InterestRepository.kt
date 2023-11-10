package me.project.upal.repository

import me.project.upal.entity.Interest
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface InterestRepository : JpaRepository<Interest, UUID> {
}