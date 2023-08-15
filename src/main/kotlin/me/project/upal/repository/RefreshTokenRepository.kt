package me.project.upal.repository

import me.project.upal.entity.RefreshToken
import org.springframework.data.repository.CrudRepository
import java.util.*

interface RefreshTokenRepository : CrudRepository<RefreshToken, UUID> {

}