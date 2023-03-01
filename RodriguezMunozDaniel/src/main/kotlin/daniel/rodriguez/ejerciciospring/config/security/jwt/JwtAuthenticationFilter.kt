package daniel.rodriguez.ejerciciospring.config.security.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import daniel.rodriguez.ejerciciospring.dto.UserDTOlogin
import daniel.rodriguez.ejerciciospring.models.User
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.*

class JwtAuthenticationFilter(
    private val jwtTokenUtil: JwtTokensUtils,
    private val authenticationManager: AuthenticationManager
) : UsernamePasswordAuthenticationFilter() {

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val credentials = ObjectMapper().readValue(request.inputStream, UserDTOlogin::class.java)

        val auth = UsernamePasswordAuthenticationToken(
            credentials?.username,
            credentials?.password
        )
        return authenticationManager.authenticate(auth)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        chain: FilterChain?,
        authResult: Authentication
    ) {
        val user = authResult.principal as User

        val token: String = jwtTokenUtil.create(user)

        response.addHeader("Authorization", token)
        response.addHeader("Access-Control-Expose-Headers", JwtTokensUtils.TOKEN_HEADER)
    }

    override fun unsuccessfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        failed: AuthenticationException
    ) {
        val error = BadCredentialsError()
        response.status = error.status
        response.contentType = "application/json"
        response.writer.append(error.toString())
    }

    private data class BadCredentialsError(
        val timestamp: Long = Date().time,
        val status: Int = 401,
        val message: String = "Error: Incorrect user or password."
    )
}