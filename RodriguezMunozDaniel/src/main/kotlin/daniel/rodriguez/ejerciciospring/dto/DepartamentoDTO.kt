package daniel.rodriguez.ejerciciospring.dto

import daniel.rodriguez.ejerciciospring.services.serializers.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class DepartamentoDTOcreacion(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID = UUID.randomUUID(),
    val nombre: String,
    val presupuesto: Double = 0.0
)

@Serializable
data class DepartamentoDTO(
    val nombre: String,
    val presupuesto: Double = 0.0,
    val empleados: List<EmpleadoDTO> = listOf()
)