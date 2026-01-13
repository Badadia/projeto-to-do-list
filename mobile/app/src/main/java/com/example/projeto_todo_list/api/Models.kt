package com.example.projeto_todo_list.api

data class LoginRequest(
    val usuario: String,
    val senha: String
)

data class LoginResponse(
    val message: String,
    val token: String
)

data class Tarefa(
    val id: Int? = null,
    val titulo: String,
    val descricao: String,
    val prioridade: String
)