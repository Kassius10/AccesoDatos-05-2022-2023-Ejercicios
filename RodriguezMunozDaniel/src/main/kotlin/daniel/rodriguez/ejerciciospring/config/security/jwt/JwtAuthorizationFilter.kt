package daniel.rodriguez.ejerciciospring.config.security.jwt

import io.netty.handler.codec.http.HttpHeaderNames.AUTHORIZATION
import daniel.rodriguez.ejerciciospring.services.UserService
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kotlinx.coroutines.runBlocking
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException

class JwtAuthorizationFilter(
    private val jwtTokensUtils: JwtTokensUtils,
    private val service: UserService,
    authManager: AuthenticationManager
) : BasicAuthenticationFilter(authManager) {

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(
        req: HttpServletRequest,
        res: HttpServletResponse,
        chain: FilterChain
    ) {
        logger.info { "Filtrando" }
        val header = req.getHeader(AUTHORIZATION.toString())

        if (header == null || !header.startsWith(JwtTokensUtils.TOKEN_PREFIX)) {
            chain.doFilter(req, res)
            return
        }
        getAuthentication(header.substring(7))?.also {
            SecurityContextHolder.getContext().authentication = it
            println(it)
        }
        chain.doFilter(req, res)
    }

    private fun getAuthentication(token: String): UsernamePasswordAuthenticationToken? = runBlocking {
        logger.info { "Obteniendo autenticación" }

        val tokenDecoded = jwtTokensUtils.decode(token) ?: return@runBlocking null

        val username = tokenDecoded.getClaim("username").toString().replace("\"", "")

        val user = service.loadUserByUsername(username)

        System.err.println(user)

        UsernamePasswordAuthenticationToken(
            user,
            null,
            user.authorities
        )
    }
}