package es.ruymi.departamentoempleadoruben

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@SpringBootApplication
@EnableCaching
@EnableR2dbcRepositories
class DepartamentoEmpleadoRubenApplication

fun main(args: Array<String>) {
    runApplication<DepartamentoEmpleadoRubenApplication>(*args)
}
