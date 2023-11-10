package me.project.upal.repository

import me.project.upal.entity.InterestCountry
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface InterestCountryRepository : JpaRepository<InterestCountry, UUID> {
}