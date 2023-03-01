package com.example.departemplespring.exceptions

sealed class EmpleadoException(message: String) : RuntimeException(message)
class EmpleadoNotFoundException(message: String) : EmpleadoException(message)
class EmpleadoBadRequestException(message: String) : EmpleadoException(message)