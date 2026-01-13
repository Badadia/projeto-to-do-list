package com.example.mobile.api

import com.example.projeto_todo_list.api.LoginRequest
import com.example.projeto_todo_list.api.LoginResponse
import com.example.projeto_todo_list.api.Tarefa
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET("tarefas")
    fun getTarefas(): Call<List<Tarefa>>
}