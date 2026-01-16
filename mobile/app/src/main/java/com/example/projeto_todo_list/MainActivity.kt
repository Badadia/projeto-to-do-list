package com.example.projeto_todo_list

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projeto_todo_list.api.RetrofitClient
import com.example.projeto_todo_list.api.Tarefa
import com.example.projeto_todo_list.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.fabAdicionar.setOnClickListener {
            Toast.makeText(this, "cadastro", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        carregarTarefas()
    }

    private fun carregarTarefas() {
        RetrofitClient.api.getTarefas().enqueue(object : Callback<List<Tarefa>> {
            override fun onResponse(call: Call<List<Tarefa>>, response: Response<List<Tarefa>>) {
                if (response.isSuccessful) {
                    val tarefas = response.body() ?: emptyList()

                    val adapter = TarefasAdapter(tarefas)
                    binding.recyclerView.adapter = adapter
                } else {
                    Toast.makeText(applicationContext, "Erro ao carregar tarefas", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Tarefa>>, t: Throwable) {
                Toast.makeText(applicationContext, "Erro de conexão", Toast.LENGTH_SHORT).show()
            }
        })
    }
}