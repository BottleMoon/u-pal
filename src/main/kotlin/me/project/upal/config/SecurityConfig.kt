package me.project.upal.config

import me.project.upal.jwt.JwtFilter
import me.project.upal.jwt.JwtTokenProvider
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
class SecurityConfig(private val jwtTokenProvider: JwtTokenProvider) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
                .cors { it.disable() }
                .csrf { it.disable() }
                .headers { it.frameOptions { it.disable() } }
                .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
                //Swagger, H2, 로그인, 회원가입 인증 필요 x
                .authorizeHttpRequests {
                    it.requestMatchers(
                            AntPathRequestMatcher("/swagger-ui/**"),
                            AntPathRequestMatcher("/v3/api-docs/**"),
                            AntPathRequestMatcher("/auth/**"),
                            PathRequest.toH2Console(),
                    ).permitAll()
                    it.anyRequest().authenticated()
                }

                .addFilterBefore(JwtFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter::class.java)
                .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}