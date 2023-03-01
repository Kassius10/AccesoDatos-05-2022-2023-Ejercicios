package es.ruymi.departamentoempleadoruben.config.websocket

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry


@Configuration
@EnableWebSocket
class ServerWebSocketConfig : WebSocketConfigurer {
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(webSocketRaquetasHandler(), "api/updates/empleados")
        registry.addHandler(webSocketRepresentantesHandler(), "api/updates/departamentos")
    }

    @Bean
    fun webSocketRaquetasHandler(): WebSocketHandler {
        return WebSocketHandler("Departamentos")
    }

    @Bean
    fun webSocketRepresentantesHandler(): WebSocketHandler {
        return WebSocketHandler("Empleados")
    }

}
