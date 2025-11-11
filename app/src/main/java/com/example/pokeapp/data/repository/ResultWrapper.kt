package com.example.pokeapp.data.repository

/**
 * Uma classe 'wrapper' genérica para representar um resultado de uma operação
 * que pode falhar (como uma chamada de rede ou banco de dados).
 * Isso cumpre o requisito de "Tratamento de Exceções".
 */
sealed class ResultWrapper<out T> {
    /**
     * Representa um resultado de sucesso, contendo os [data].
     */
    data class Success<out T>(val data: T) : ResultWrapper<T>()

    /**
     * Representa um resultado de falha, contendo uma [message] de erro.
     */
    data class Error(val message: String) : ResultWrapper<Nothing>()
}