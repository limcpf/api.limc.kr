package api.limc.kr.blog.config.auth

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration

class SecurityConfiguration {
    @Autowired private lateinit var jwtAuthenticationFilter:JwtAuthenticationFilter
    @Autowired private lateinit var unAuthenticatedHandler:JwtAuthenticationEntryPoint

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer? {
        return WebSecurityCustomizer { web: WebSecurity -> web.ignoring().requestMatchers("/swagger-ui/**", "/private/login") }
    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests()
                .requestMatchers("/public/**")
                .permitAll()
            .and()
                .authorizeHttpRequests()
                .requestMatchers("/private/**")
                .hasAnyRole("ADMIN")
            .and()
                .exceptionHandling()
                .authenticationEntryPoint(unAuthenticatedHandler)
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .httpBasic().disable()
            .formLogin().disable()
            .cors().disable()
            .csrf().disable()

        return http.build()
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManagerBean(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager? {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun encoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

}