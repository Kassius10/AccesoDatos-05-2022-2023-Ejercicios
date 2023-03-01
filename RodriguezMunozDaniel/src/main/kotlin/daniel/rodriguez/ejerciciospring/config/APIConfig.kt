package daniel.rodriguez.ejerciciospring.config

import org.springframework.context.annotation.Configuration

@Configuration
class APIConfig {
    companion object {
        const val API_PATH = "/ejercicioSpring"
        const val API_VERSION = "1.0"
    }
}