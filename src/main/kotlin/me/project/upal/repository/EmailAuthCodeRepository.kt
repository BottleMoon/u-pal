package me.project.upal.repository

import me.project.upal.entity.EmailAuthCode
import org.springframework.data.repository.CrudRepository

interface EmailAuthCodeRepository : CrudRepository<EmailAuthCode, String> {
}