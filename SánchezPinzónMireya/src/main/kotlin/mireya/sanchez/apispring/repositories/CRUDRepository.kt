package mireya.sanchez.apispring.repositories

import kotlinx.coroutines.flow.Flow

interface CRUDRepository<T, ID> {
    suspend fun findAll(): Flow<T>
    suspend fun findById(id: ID): T?
    suspend fun save(entity: T): T
    suspend fun update(id: ID, entity: T): T?
    suspend fun deleteById(id: ID): T?
}