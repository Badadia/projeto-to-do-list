package com.example.projeto_todo_list

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projeto_todo_list.api.RetrofitClient
import com.example.projeto_todo_list.api.Tarefa
import com.example.projeto_todo_list.databinding.ActivityFormTarefaBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormTarefaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormTarefaBinding
    private var tarefaId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormTarefaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prioridades = arrayOf("Alta", "Média", "Baixa")
        val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_item, prioridades)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPrioridade.adapter = adapterSpinner

        if (intent.hasExtra("id")) {
            tarefaId = intent.getIntExtra("id", 0)
            binding.etTitulo.setText(intent.getStringExtra("titulo"))
            binding.etDescricao.setText(intent.getStringExtra("descricao"))

            val prioridade = intent.getStringExtra("prioridade")
            val posicao = prioridades.indexOf(prioridade)
            if (posicao >= 0) binding.spinnerPrioridade.setSelection(posicao)

            binding.btnSalvar.text = "ATUALIZAR"
            binding.btnExcluir.visibility = View.VISIBLE
        }

        binding.btnSalvar.setOnClickListener {
            salvarTarefa()
        }

        binding.btnExcluir.setOnClickListener {
            excluirTarefa()
        }
    }

    private fun salvarTarefa() {
        val titulo = binding.etTitulo.text.toString()
        val descricao = binding.etDescricao.text.toString()
        val prioridade = binding.spinnerPrioridade.selectedItem.toString()

        val tarefa = Tarefa(id = tarefaId, titulo = titulo, descricao = descricao, prioridade = prioridade)

        val call = if (tarefaId == null) {
            RetrofitClient.api.criarTarefa(tarefa)
        } else {
            RetrofitClient.api.atualizarTarefa(tarefaId!!, tarefa)
        }

        call.enqueue(object : Callback<Tarefa> {
            override fun onResponse(call: Call<Tarefa>, response: Response<Tarefa>) {
                if (response.isSuccessful) {
                    Toast.makeText(applicationContext, "Salvo com sucesso!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Erro ao salvar", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Tarefa>, t: Throwable) {
                Toast.makeText(applicationContext, "Erro de conexão", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun excluirTarefa() {
        tarefaId?.let { id ->
            RetrofitClient.api.deletarTarefa(id).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(applicationContext, "Excluído!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {}
            })
        }
    }
}