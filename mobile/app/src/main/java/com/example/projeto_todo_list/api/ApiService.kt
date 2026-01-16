package com.example.projeto_todo_list.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET("tarefas")
    fun getTarefas(): Call<List<Tarefa>>

    @POST("tarefas")
    fun criarTarefa(@Body tarefa: Tarefa): Call<Tarefa>

    @PUT("tarefas/{id}")
    fun atualizarTarefa(@Path("id") id: Int, @Body tarefa: Tarefa): Call<Tarefa>

    @DELETE("tarefas/{id}")
    fun deletarTarefa(@Path("id") id: Int): Call<Void>
}