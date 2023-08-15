package me.project.upal.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import me.project.upal.config.logger
import org.apache.commons.lang3.StringUtils
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean


class JwtFilter(
        private val jwtTokenProvider: JwtTokenProvider
) : GenericFilterBean() {
    private val AUTHORIZATION_HEADER: String = "Authorization"
    val log = logger()

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val httpServletRequest = request as HttpServletRequest
        val jwt = resolveToken(httpServletRequest)
        val requestURI = httpServletRequest.requestURI

        if (StringUtils.isNotBlank(jwt) && jwtTokenProvider.validateToken(jwt)) {
            val authentication: Authentication = jwtTokenProvider.getAuthentication(jwt)
            SecurityContextHolder.getContext().authentication = authentication
            log.info("Authenticated user ${authentication.name}, setting security context")
        } else {
            log.info("No valid JWT token found, uri: $requestURI")
        }
        chain?.doFilter(request, response)
    }

    private fun resolveToken(request: HttpServletRequest): String {
        val bearerToken = request.getHeader(AUTHORIZATION_HEADER)

        if (StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7)
        }
        return ""
    }
}