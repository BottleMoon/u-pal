package me.project.upal.jwt

import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import me.project.upal.config.logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import java.security.Key
import java.security.SignatureException
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors

@Component
class JwtTokenProvider(
        @Value("\${jwt.secret}")
        private val secret: String,
        @Value("\${jwt.token-validity-in-second}")
        private val tokenValidityInSecond: Long,
        @Value("\${jwt.refresh-token-validity-in-day}")
        private val refreshTokenValidityInDay: Long
) {
    private val tokenValidityTime: Long = TimeUnit.SECONDS.toMillis(tokenValidityInSecond)
    private val refreshTokenValidityTime: Long = TimeUnit.DAYS.toMillis(refreshTokenValidityInDay)
    private val key: Key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))
    val log = logger()

    fun createToken(authentication: Authentication): String {
        val authorities: String = getAuthorities(authentication)

        val now: Long = Date().time
        val accessTokenExpiresIn = Date(now + tokenValidityTime)

        return Jwts.builder()
                .setSubject(authentication.name)
                .claim("auth", authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(accessTokenExpiresIn)
                .compact()
    }

    fun createRefreshToken(authentication: Authentication): String {
        val authorities: String = getAuthorities(authentication)

        val now: Long = Date().time
        val accessRefreshTokenExpiresIn = Date(now + refreshTokenValidityTime)

        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(accessRefreshTokenExpiresIn)
                .compact()

    }

    fun getAuthorities(authentication: Authentication): String {
        return authentication.authorities.stream()
                .map { obj: GrantedAuthority -> obj.authority }
                .collect(Collectors.joining(","))
    }

    fun getAuthentication(token: String): Authentication {
        val claims: Claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .body

        val authorities = claims["auth"].toString().split(",")
                .map { SimpleGrantedAuthority(it) }
                .toList()

        val principal: User = User(claims.subject, "", authorities)

        return UsernamePasswordAuthenticationToken(principal, token, authorities)
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            return true
        } catch (e: Exception) {
            when (e) {
                is MalformedJwtException, is SignatureException -> log.warn("Invalid JWT signature")
                is ExpiredJwtException -> log.warn("Expired JWT token")
                is UnsupportedJwtException -> log.warn("Unsupported JWT token")
                is IllegalArgumentException -> log.warn("Invalid JWT token")
                else -> log.warn("Invalid JWT token")
            }
        }

        return false
    }


}